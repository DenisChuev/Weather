package dc.weather.business.repos

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import dc.weather.business.ApiProvider
import dc.weather.business.model.WeatherCurrentModel
import dc.weather.business.room.WeatherCurrentEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {
    private val gson = Gson()
    private val dbAccess = db.getWeatherDao()

    @SuppressLint("CheckResult")
    fun reloadData(lat: String, lon: String) {
        Observable.zip(
            api.getWeatherApi().getCurrentWeather(lat, lon),
            api.getGeoCodingApi().getCityByCoords(lat, lon).map {
                it.asSequence()
                    .map { model -> model.name }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            { weatherData, geoCode -> ServerResponse(geoCode, weatherData) }
        )
            .subscribeOn(Schedulers.io())
            .doOnNext {
                dbAccess.insertWeatherData(
                    WeatherCurrentEntity(
                        data = gson.toJson(it.weatherData),
                        city = it.cityName
                    )
                )
            }
            .onErrorResumeNext {
                Observable.just(
                    ServerResponse(
                        dbAccess.getWeatherData().city,
                        gson.fromJson(
                            dbAccess.getWeatherData().data,
                            WeatherCurrentModel::class.java
                        ),
                        it
                    )
                )

            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dataEmitter.onNext(it)
            }, {
                Log.d("MainRepository", "reload $it")
            })
    }

    data class ServerResponse(
        val cityName: String,
        val weatherData: WeatherCurrentModel,
        val error: Throwable? = null
    )
}
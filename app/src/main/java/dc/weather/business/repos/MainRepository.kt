package dc.weather.business.repos

import dc.weather.business.ApiProvider
import dc.weather.business.model.WeatherCurrentModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {

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
            .doOnNext {/*TODO добавление объекта в бд*/ }
            /*.onErrorResumeNext {} TODO Извлечение объекта из бд*/
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
    }

    data class ServerResponse(
        val cityName: String,
        val weatherData: WeatherCurrentModel,
        val error: Throwable? = null
    )
}
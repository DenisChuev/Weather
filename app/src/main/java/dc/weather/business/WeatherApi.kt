package dc.weather.business

import dc.weather.business.model.Weather
import dc.weather.business.model.WeatherCurrentModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = "bbde336b2cf91b1b09f9e126f3cb4250",
        @Query("lang") lang: String = "ru"
    ): Observable<WeatherCurrentModel>

}
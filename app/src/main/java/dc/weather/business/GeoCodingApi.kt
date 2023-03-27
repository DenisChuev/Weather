package dc.weather.business

import dc.weather.business.model.GeoCodeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {
    @GET("geo/1.0/reverse?")
    fun getCityByCoords(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String = "10",
        @Query("appid") appid: String = "bbde336b2cf91b1b09f9e126f3cb4250",
    ): Observable<List<GeoCodeModel>>

    @GET("geo/1.0/direct")
    fun getCityByName(
        @Query("q") name: String,
        @Query("limit") limit: String = "10",
        @Query("appid") appid: String = "bbde336b2cf91b1b09f9e126f3cb4250"
    ): Observable<List<GeoCodeModel>>
}
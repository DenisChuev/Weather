package dc.weather.business.room

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE id = 1")
    fun getWeatherData(): WeatherCurrentEntity

    @Insert
    fun insertWeatherData(data: WeatherCurrentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeatherData(data: WeatherCurrentEntity)

    @Delete
    fun deleteWeatherData(data: WeatherCurrentEntity)
}
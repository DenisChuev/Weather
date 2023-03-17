package dc.weather.business.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherCurrentEntity(
    @PrimaryKey val id: Int = 1,
    val city: String,
    val data: String
)

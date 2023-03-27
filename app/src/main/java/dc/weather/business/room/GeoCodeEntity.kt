package dc.weather.business.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import dc.weather.business.model.LocalNames

@Entity(tableName = "GeoCode", primaryKeys = ["lat", "lon"])
class GeoCodeEntity(
    val name: String,
    @Embedded val local_names: LocalNames,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String,
    var isFavorite: Boolean = false
)
package dc.weather.business

import dc.weather.business.model.GeoCodeModel
import dc.weather.business.room.GeoCodeEntity

fun GeoCodeModel.mapToEntity() = GeoCodeEntity(
    this.name,
    this.local_names,
    this.lat,
    this.lon,
    this.country,
    this.state ?: "",
    this.isFavorite
)

fun GeoCodeEntity.mapToModel() = GeoCodeModel(
    this.name,
    local_names,
    this.lat,
    this.lon,
    this.country,
    this.state,
    this.isFavorite
)
package dc.weather.business.model

data class GeoCodeModel(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String,
    val isFavorite: Boolean = false
)

data class LocalNames(
    val be: String,
    val cy: String,
    val en: String,
    val fr: String,
    val he: String,
    val ko: String,
    val mk: String,
    val ru: String
)
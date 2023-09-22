package app.android.seedapp.application.data.models.statistics

import com.google.gson.annotations.SerializedName

data class GetUserStatisticsResponse(
    @SerializedName("dates") val datesStatistics: ArrayList<DatesStatistics> = arrayListOf(),
    @SerializedName("place") val placesStatistics: ArrayList<PlacesStatistics> = arrayListOf(),
    @SerializedName("municipality") val municipalityStatistics: ArrayList<MunicipalityStatistics> = arrayListOf(),
    @SerializedName("departments") val departmentsStatistics: ArrayList<DepartmentsStatistics> = arrayListOf(),
    @SerializedName("genre") val genreStatistics: ArrayList<GenreStatistics> = arrayListOf()
)

data class DatesStatistics(
    @SerializedName("date") val date: String = "",
    @SerializedName("registers") val registers: Int = 0
)

data class PlacesStatistics(
    @SerializedName("place") val place: String = "",
    @SerializedName("registers") val registers: Int = 0
)

data class MunicipalityStatistics(
    @SerializedName("mun") val municipality: String = "",
    @SerializedName("registers") val registers: Int = 0
)

data class DepartmentsStatistics(
    @SerializedName("dep") val Department: String = "",
    @SerializedName("registers") val registers: Int = 0
)

data class GenreStatistics(
    @SerializedName("genre") val Department: String = "",
    @SerializedName("registers") val registers: Int = 0
)
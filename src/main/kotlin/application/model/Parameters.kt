package application.model

// Сдвиг сигмоиды влияния продолжительности контакта
var durationInfluenceParameter = 0.0
// Параметры восприимчивости агентов к различным инфекциям
var susceptibilityParameterMap = mapOf(
    "fluA" to 0.0, "fluB" to 0.0,
    "RV" to 30.0, "RSV" to 0.0,
    "AdV" to 0.0, "PIV" to 0.0,
    "CoV" to 0.0
)
// Параметры влияния температуры воздуха на вероятность заражения
var temperatureParameterMap = mapOf(
    "fluA" to 0.0, "fluB" to 0.0,
    "RV" to 0.0, "RSV" to 0.0,
    "AdV" to 0.0, "PIV" to 0.0,
    "CoV" to 0.0
)
// Продолжительности типоспецифических иммунитетов
var immunityMap = mapOf(
    "fluA" to 0, "fluB" to 0,
    "RV" to 0, "RSV" to 0,
    "AdV" to 0, "PIV" to 0,
    "CoV" to 0
)

// Инициализация параметров
fun setParameters(
    durationInfluenceParameter: Double,
    susceptibilityInfluenceFluAParameter: Double,
    susceptibilityInfluenceFluBParameter: Double,
    susceptibilityInfluenceRVParameter: Double,
    susceptibilityInfluenceRSVParameter: Double,
    susceptibilityInfluenceAdVParameter: Double,
    susceptibilityInfluencePIVParameter: Double,
    susceptibilityInfluenceCoVParameter: Double,

    temperatureInfluenceFluAParameter: Double,
    temperatureInfluenceFluBParameter: Double,
    temperatureInfluenceRVParameter: Double,
    temperatureInfluenceRSVParameter: Double,
    temperatureInfluenceAdVParameter: Double,
    temperatureInfluencePIVParameter: Double,
    temperatureInfluenceCoVParameter: Double,

    immunityDurationFluAParameter: Int,
    immunityDurationFluBParameter: Int,
    immunityDurationRVParameter: Int,
    immunityDurationRSVParameter: Int,
    immunityDurationAdVParameter: Int,
    immunityDurationPIVParameter: Int,
    immunityDurationCoVParameter: Int
) {
    application.model.durationInfluenceParameter = durationInfluenceParameter
    susceptibilityParameterMap = mapOf(
        "fluA" to susceptibilityInfluenceFluAParameter, "fluB" to susceptibilityInfluenceFluBParameter,
        "RV" to susceptibilityInfluenceRVParameter, "RSV" to susceptibilityInfluenceRSVParameter,
        "AdV" to susceptibilityInfluenceAdVParameter, "PIV" to susceptibilityInfluencePIVParameter,
        "CoV" to susceptibilityInfluenceCoVParameter
    )
    temperatureParameterMap = mapOf(
        "fluA" to temperatureInfluenceFluAParameter, "fluB" to temperatureInfluenceFluBParameter,
        "RV" to temperatureInfluenceRVParameter, "RSV" to temperatureInfluenceRSVParameter,
        "AdV" to temperatureInfluenceAdVParameter, "PIV" to temperatureInfluencePIVParameter,
        "CoV" to temperatureInfluenceCoVParameter
    )
    immunityMap = mapOf(
        "fluA" to immunityDurationFluAParameter, "fluB" to immunityDurationFluBParameter,
        "RV" to immunityDurationRVParameter, "RSV" to immunityDurationRSVParameter,
        "AdV" to immunityDurationAdVParameter, "PIV" to immunityDurationPIVParameter,
        "CoV" to immunityDurationCoVParameter
    )
}

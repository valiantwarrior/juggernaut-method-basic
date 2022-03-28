package kr.valor.juggernaut.domain.session.model

data class RoutineIntensity(
    val repetitions: Int,
    val intensityPercentage: Double
) {

    val approximationIntensityPercentageValue: Number
        get() {
            val approximation = intensityPercentage * 100
            return when(approximation % 1.0) {
                in 0.0 .. 0.1 -> approximation.toInt()
                else -> approximation
            }
        }

}

inline fun RoutineIntensity.toRoutineModel(tmWeights: Int, actualRepetitions: Int? = null, transform: (Double) -> Int) =
    RoutineFactory.create(
        weights = transform(tmWeights * intensityPercentage),
        reps = actualRepetitions ?: repetitions,
        baseIntensity = this
    )
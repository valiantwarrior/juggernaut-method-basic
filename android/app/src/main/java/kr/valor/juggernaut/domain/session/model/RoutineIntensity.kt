package kr.valor.juggernaut.domain.session.model

data class RoutineIntensity(
    val repetitions: Int,
    val intensityPercentage: Double
)

inline fun RoutineIntensity.toRoutineModel(tmWeights: Int, actualRepetitions: Int? = null, transform: (Double) -> Int) =
    actualRepetitions?.let { actualReps ->
        Routine(
            weights = transform(tmWeights * intensityPercentage),
            reps = actualReps
        )
    } ?: Routine(
        weights = transform(tmWeights * intensityPercentage),
        reps = repetitions
    )
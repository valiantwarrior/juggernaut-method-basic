package kr.valor.juggernaut.domain.session.model

data class RoutineIntensity(
    val repetitions: Int,
    val intensityPercentage: Double
)

inline fun RoutineIntensity.toRoutineModel(tmWeights: Double, transform: (Double) -> Double) =
    Routine(
        weights = transform(tmWeights * intensityPercentage),
        reps = repetitions
    )

inline fun List<RoutineIntensity>.toRoutineModels(tmWeights: Double, transform: (Double) -> Double) =
    map { it.toRoutineModel(tmWeights, transform) }
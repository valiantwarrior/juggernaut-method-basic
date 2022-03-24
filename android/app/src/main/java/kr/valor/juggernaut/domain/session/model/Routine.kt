package kr.valor.juggernaut.domain.session.model

data class Routine(
    val routineId: Long,
    val weights: Int,
    val reps: Int
)

object RoutineFactory {

    private var routineId: Long = 0L

    fun create(weights: Int, reps: Int): Routine =
        Routine(
            routineId = routineId++,
            weights = weights,
            reps = reps
        )

}
package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

data class Session(
    val sessionId: Long,
    val category: LiftCategory,
    val progressions: Progressions,
    val tmWeights: Double,
    val routines: SessionRoutine
)

data class Progressions(
    val phase: Phase,
    val microCycle: MicroCycle
)

data class Routine(
    val weights: Double,
    val reps: Int
)

data class SessionRoutine(
    val warmupRoutines: List<Routine>,
    val amrapRoutine: Routine
)

interface RoutinesProviderDelegate {
    fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine
}

interface RoutinesPropertyDelegate {
    fun ceiling(input: Double): Double
}

interface RoutinesProviderDelegateFactory {
    fun provide(progressions: Progressions, ceilUserPreferences: Double?): RoutinesProviderDelegate
}
package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

typealias Progression = Session.Progression

data class Session(
    val sessionId: Long,
    val category: LiftCategory,
    val tmWeights: Int,
    val progression: Progression,
    val isCompleted: Boolean,
    val routines: List<Routine>,
) {

    val warmupRoutines: List<Routine>?
        get() = getOrNull(progression.isAmrapSession) { routines.dropLast(1) }

    val amrapRoutine: Routine?
        get() = getOrNull(progression.isAmrapSession) { routines.last() }

    val deloadRoutines: List<Routine>?
        get() = getOrNull(!progression.isAmrapSession) { routines }

    private inline fun <T> getOrNull(condition: Boolean, get: () -> T): T? =
        if (condition) get() else null

    data class Progression(
        val methodCycle: MethodCycle,
        val phase: Phase,
        val microCycle: MicroCycle
    ) {

        val isAmrapSession: Boolean
            get() = when(microCycle) {
                MicroCycle.DELOAD -> false
                else -> true
            }

        val baseAmrapRepetitions: Int
            get() {
                return if (isAmrapSession) {
                    phase.baseAmrapRepetitions
                } else {
                    DELOAD_SESSION_INDICATOR
                }
            }

        companion object {
            const val DELOAD_SESSION_INDICATOR = -1
        }

    }

}
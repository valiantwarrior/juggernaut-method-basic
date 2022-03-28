package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.Companion.TOTAL_PHASE_COUNT
import java.time.LocalDateTime

typealias Progression = Session.Progression

data class Session(
    val sessionId: Long,
    val category: LiftCategory,
    val tmWeights: Int,
    val progression: Progression,
    val isCompleted: Boolean,
    val completedLocalDateTime: LocalDateTime?,
    val sessionOrdinal: Int?,
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

        val weekOrdinal: Int
            get() {
                val basePhaseWeekOrdinal = phase.ordinal * TOTAL_PHASE_COUNT
                val baseMicrocycleWeekOrdinal = microCycle.ordinal + 1

                return basePhaseWeekOrdinal + baseMicrocycleWeekOrdinal
            }

        companion object {
            const val DELOAD_SESSION_INDICATOR = -1
        }

    }

}
package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

data class SessionProgression(
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
            val basePhaseWeekOrdinal = phase.ordinal * Phase.TOTAL_PHASE_COUNT
            val baseMicrocycleWeekOrdinal = microCycle.ordinal + 1

            return basePhaseWeekOrdinal + baseMicrocycleWeekOrdinal
        }

    companion object {
        const val DELOAD_SESSION_INDICATOR = -1
    }

}
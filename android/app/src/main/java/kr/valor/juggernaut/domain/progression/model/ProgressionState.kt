package kr.valor.juggernaut.domain.progression.model

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.MicroCycle.Companion.TOTAL_MICROCYCLE_COUNT
import kr.valor.juggernaut.common.Phase.Companion.TOTAL_PHASE_COUNT
import kr.valor.juggernaut.domain.session.model.Progression

sealed class ProgressionState {
    object None: ProgressionState()
    data class OnGoing(val currentUserProgression: UserProgression): ProgressionState()
    data class Done(val latestUserProgression: UserProgression): ProgressionState()
}

data class UserProgression(
    val methodCycle: MethodCycle,
    val phase: Phase,
    val microCycle: MicroCycle
) {

    val serializedValue: Triple<Int, String, String>
        get() = Triple(methodCycle.value, phase.name, microCycle.name)
    
    val currentMethodMilestone: Int
        get() {
            val phaseMilestone = phase.ordinal * TOTAL_PHASE_COUNT
            val microCycleMilestone = microCycle.ordinal + 1

            return phaseMilestone + microCycleMilestone
        }

    fun toSessionProgression() = Progression(
        methodCycle, phase, microCycle
    )

    companion object {
        const val OVERALL_METHOD_MILESTONE = TOTAL_PHASE_COUNT * TOTAL_MICROCYCLE_COUNT
    }
}
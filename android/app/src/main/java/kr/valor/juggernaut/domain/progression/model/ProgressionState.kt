package kr.valor.juggernaut.domain.progression.model

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.entity.SessionEntity

sealed class ProgressionState {
    object None: ProgressionState()
    data class OnGoing(val currentUserProgression: UserProgression): ProgressionState()
    data class Done(val latestUserProgression: UserProgression): ProgressionState()
}

data class UserProgression(
    val methodCycle: MethodCycle,
    val phase: Phase,
    val microCycle: MicroCycle
)

fun SessionEntity.extractUserProgression() = UserProgression(
    methodCycle = MethodCycle(value = methodCycle),
    phase = Phase.valueOf(phaseName),
    microCycle = MicroCycle.valueOf(microCycleName)
)
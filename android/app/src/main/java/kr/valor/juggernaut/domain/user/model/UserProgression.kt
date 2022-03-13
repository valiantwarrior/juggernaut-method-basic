package kr.valor.juggernaut.domain.user.model

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.entity.SessionEntity

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
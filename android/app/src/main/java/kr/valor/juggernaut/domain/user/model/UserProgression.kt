package kr.valor.juggernaut.domain.user.model

import kr.valor.juggernaut.common.*

data class UserProgression(
    val methodCycle: MethodCycle,
    val phase: Phase,
    val microCycle: MicroCycle,
    val liftCategory: LiftCategory
)
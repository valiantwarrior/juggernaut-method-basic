package kr.valor.juggernaut.domain.user.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

data class UserProgression(
    val methodCycle: Int,
    val phase: Phase,
    val microCycle: MicroCycle,
    val liftCategory: LiftCategory
)
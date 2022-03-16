package kr.valor.juggernaut.domain.trainingmax.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.Phase

data class TrainingMax(
    val id: Long = 0L,
    val methodCycle: MethodCycle,
    val phase: Phase,
    val liftCategory: LiftCategory,
    val trainingMaxWeights: Int,
    val lastUpdatedAt: Long
)
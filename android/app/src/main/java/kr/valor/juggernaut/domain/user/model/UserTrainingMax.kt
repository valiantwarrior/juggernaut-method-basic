package kr.valor.juggernaut.domain.user.model

import kr.valor.juggernaut.common.LiftCategory

data class UserTrainingMax(
    val id: Long,
    val liftCategory: LiftCategory,
    val trainingMaxWeights: Int,
    val lastUpdatedAt: Long
)
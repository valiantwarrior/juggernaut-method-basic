package kr.valor.juggernaut.domain.user.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface UserRepository {
    suspend fun getUserTrainingMax(liftCategory: LiftCategory): UserTrainingMax
    suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long
    fun createUserTrainingMaxEntity(tmWeights: Int, liftCategory: LiftCategory): UserTrainingMaxEntity
    fun getUserProgression(): Flow<UserProgression>
}
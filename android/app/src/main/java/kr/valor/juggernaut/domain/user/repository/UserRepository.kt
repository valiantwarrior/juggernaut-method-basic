package kr.valor.juggernaut.domain.user.repository

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface UserRepository {
    suspend fun getUserTrainingMax(liftCategory: LiftCategory): UserTrainingMax
    suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long
    fun createUserTrainingMaxEntity(tmWeights: Int, liftCategory: LiftCategory): UserTrainingMaxEntity
    fun getUserProgression(): Flow<UserProgression>
    suspend fun updateUserProgression(microCycle: MicroCycle)
    suspend fun updateUserProgression(methodCycle: Int)
    suspend fun updateUserProgression(phase: Phase)
    suspend fun updateUserProgression(liftCategory: LiftCategory)

    @VisibleForTesting suspend fun clearUserTrainingMax()
    @VisibleForTesting suspend fun clearUserProgression()
}
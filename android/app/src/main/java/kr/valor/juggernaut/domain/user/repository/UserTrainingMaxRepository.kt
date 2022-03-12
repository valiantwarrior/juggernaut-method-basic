package kr.valor.juggernaut.domain.user.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface UserTrainingMaxRepository {
    fun getAllUserTrainingMaxes(): Flow<List<UserTrainingMax>>
    suspend fun getUserTrainingMaxByLiftCategory(liftCategory: LiftCategory): UserTrainingMax
    suspend fun insertUserTrainingMax(trainingMax: UserTrainingMax): Long
    fun createUserTrainingMax(rawTmWeights: Double, liftCategory: LiftCategory): UserTrainingMax
    suspend fun clear()
}
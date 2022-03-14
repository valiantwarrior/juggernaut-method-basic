package kr.valor.juggernaut.domain.user.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface UserTrainingMaxRepository {

    fun getAllUserTrainingMaxes(): Flow<List<UserTrainingMax>>

    suspend fun findUserTrainingMaxesByUserProgression(userProgression: UserProgression): List<UserTrainingMax>

    suspend fun insertUserTrainingMax(trainingMax: UserTrainingMax): Long

    fun createUserTrainingMax(rawTmWeights: Double, liftCategory: LiftCategory, userProgression: UserProgression): UserTrainingMax

    suspend fun clear()

}
package kr.valor.juggernaut.domain.trainingmax.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax

interface TrainingMaxRepository {

    fun getAllUserTrainingMaxes(): Flow<List<TrainingMax>>

    suspend fun findUserTrainingMaxesByUserProgression(userProgression: UserProgression): List<TrainingMax>

    suspend fun deleteUserTrainingMaxesByMethodCycle(methodCycle: MethodCycle)

    suspend fun insertUserTrainingMax(trainingMax: TrainingMax): Long

    fun createUserTrainingMax(liftCategory: LiftCategory, inputWeights: Double, userProgression: UserProgression): TrainingMax

    suspend fun clear()

}
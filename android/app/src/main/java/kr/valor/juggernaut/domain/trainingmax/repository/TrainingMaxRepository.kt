package kr.valor.juggernaut.domain.trainingmax.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax

interface TrainingMaxRepository {

    fun getAllTrainingMaxes(): Flow<List<TrainingMax>>

    suspend fun findTrainingMaxesByUserProgression(userProgression: UserProgression): List<TrainingMax>

    suspend fun deleteTrainingMaxesByMethodCycle(methodCycle: MethodCycle)

    suspend fun insertTrainingMax(trainingMax: TrainingMax): Long

    fun createTrainingMax(liftCategory: LiftCategory, inputWeights: Int, methodCycle: MethodCycle, phase: Phase): TrainingMax

    suspend fun clear()

}
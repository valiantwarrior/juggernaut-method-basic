package kr.valor.juggernaut.data.trainingmax.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.domain.progression.model.UserProgression

interface TrainingMaxDataSource {

    suspend fun insertTrainingMaxEntity(entity: TrainingMaxEntity): Long

    suspend fun findTrainingMaxEntitiesByMethodCycleAndPhase(methodCycleValue: Int, phaseName: String): List<TrainingMaxEntity>

    suspend fun deleteTrainingMaxesByMethodCycle(methodCycle: Int)

    fun getAllTrainingMaxEntities(): Flow<List<TrainingMaxEntity>>

    suspend fun clear()

}
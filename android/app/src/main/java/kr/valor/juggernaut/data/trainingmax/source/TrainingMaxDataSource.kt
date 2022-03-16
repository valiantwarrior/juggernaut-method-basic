package kr.valor.juggernaut.data.trainingmax.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.domain.progression.model.UserProgression

interface TrainingMaxDataSource {

    suspend fun insertUserTrainingMaxEntity(entity: TrainingMaxEntity): Long

    suspend fun findUserTrainingMaxEntitiesByUserProgression(userProgression: UserProgression): List<TrainingMaxEntity>

    suspend fun deleteUserTrainingMaxesByMethodCycle(methodCycle: Int)

    fun getUserTrainingMaxEntities(): Flow<List<TrainingMaxEntity>>

    suspend fun clear()

}
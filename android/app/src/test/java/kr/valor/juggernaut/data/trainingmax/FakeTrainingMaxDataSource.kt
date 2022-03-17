package kr.valor.juggernaut.data.trainingmax

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.data.trainingmax.source.TrainingMaxDataSource

class FakeTrainingMaxDataSource: TrainingMaxDataSource {

    private var entityId: Long = 0L

    private val inMemoryStorage = mutableListOf<TrainingMaxEntity>()

    override suspend fun insertTrainingMaxEntity(entity: TrainingMaxEntity): Long {
        inMemoryStorage.add(entity)
        return entityId
    }

    override suspend fun findTrainingMaxEntitiesByMethodCycleAndPhase(methodCycleValue: Int, phaseName: String): List<TrainingMaxEntity> =
        inMemoryStorage.filter { entity ->
            entity.methodCycleValue == methodCycleValue &&
                    entity.phaseName == phaseName
        }

    override suspend fun deleteTrainingMaxesByMethodCycle(methodCycle: Int) {
        inMemoryStorage.removeAll { it.methodCycleValue == methodCycle }
    }

    override fun getAllTrainingMaxEntities(): Flow<List<TrainingMaxEntity>> {
        return flowOf(inMemoryStorage)
    }

    override suspend fun clear() {
        inMemoryStorage.clear()
    }
}
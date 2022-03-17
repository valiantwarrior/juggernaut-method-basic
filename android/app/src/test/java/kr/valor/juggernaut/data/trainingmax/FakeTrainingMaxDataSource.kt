package kr.valor.juggernaut.data.trainingmax

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.data.trainingmax.source.TrainingMaxDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression

class FakeTrainingMaxDataSource: TrainingMaxDataSource {

    private var entityId: Long = 0L

    private val inMemoryStorage = mutableListOf<TrainingMaxEntity>()

    override suspend fun insertUserTrainingMaxEntity(entity: TrainingMaxEntity): Long {
        inMemoryStorage.add(entity)
        return entityId
    }

    override suspend fun findUserTrainingMaxEntitiesByMethodCycleAndPhase(methodCycleValue: Int, phaseName: String): List<TrainingMaxEntity> =
        inMemoryStorage.filter { entity ->
            entity.methodCycle == methodCycleValue &&
                    entity.phaseName == phaseName
        }

    override suspend fun deleteUserTrainingMaxesByMethodCycle(methodCycle: Int) {
        inMemoryStorage.removeAll { it.methodCycle == methodCycle }
    }

    override fun getUserTrainingMaxEntities(): Flow<List<TrainingMaxEntity>> {
        return flowOf(inMemoryStorage)
    }

    override suspend fun clear() {
        inMemoryStorage.clear()
    }
}
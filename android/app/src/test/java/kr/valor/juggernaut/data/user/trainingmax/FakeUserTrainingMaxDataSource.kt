package kr.valor.juggernaut.data.user.trainingmax

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.user.trainingmax.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.data.user.trainingmax.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.domain.user.model.UserProgression

class FakeUserTrainingMaxDataSource: UserTrainingMaxDataSource {

    private var entityId: Long = 0L

    private val inMemoryStorage = mutableListOf<UserTrainingMaxEntity>()

    override suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long {
        inMemoryStorage.add(entity)
        return entityId
    }

    override suspend fun findUserTrainingMaxEntitiesByUserProgression(userProgression: UserProgression): List<UserTrainingMaxEntity> =
        inMemoryStorage.filter { entity ->
            entity.methodCycle == userProgression.methodCycle.value &&
                    entity.phaseName == userProgression.phase.name
        }


    override fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>> {
        return flowOf(inMemoryStorage)
    }

    override suspend fun clear() {
        inMemoryStorage.clear()
    }
}
package kr.valor.juggernaut.data.session

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.source.SessionDataSource

class FakeSessionDataSource: SessionDataSource {

    private val inMemoryStorage = mutableListOf<SessionEntity>()

    private var sessionEntityId: Long = 0L

    override suspend fun insertSessionEntity(entity: SessionEntity): Long {
        val entityWithFakeId = entity.copy(id = sessionEntityId++)
        inMemoryStorage.add(entityWithFakeId)
        return entityWithFakeId.id
    }

    override fun findSessionEntitiesByUserProgression(methodCycleValue: Int, phaseName: String, microCycleName: String): Flow<List<SessionEntity>> =
        flowOf(
            inMemoryStorage.filter { entity ->
                entity.methodCycleValue == methodCycleValue
                        && entity.phaseName == phaseName
                        && entity.microCycleName == microCycleName
            }
        )

    override suspend fun deleteSessionEntity(entity: SessionEntity) {
        inMemoryStorage.remove(entity)
    }

    override suspend fun deleteSessionEntitiesByMethodCycle(methodCycle: Int) {
        inMemoryStorage.removeAll { it.methodCycleValue == methodCycle }
    }

    override suspend fun findSessionEntityById(id: Long): SessionEntity {
        return inMemoryStorage.find { it.id == id }!!
    }

    override fun getAllSessionEntities(): Flow<List<SessionEntity>> {
        return flowOf(inMemoryStorage)
    }

    override suspend fun clear() {
        inMemoryStorage.clear()
    }
}
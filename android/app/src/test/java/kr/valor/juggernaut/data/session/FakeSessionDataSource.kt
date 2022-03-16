package kr.valor.juggernaut.data.session

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.model.extractUserProgression

class FakeSessionDataSource: SessionDataSource {

    private val inMemoryStorage = mutableListOf<SessionEntity>()

    private var sessionEntityId: Long = 0L

    override suspend fun insertSessionEntity(entity: SessionEntity): Long {
        val entityWithFakeId = entity.copy(id = sessionEntityId++)
        inMemoryStorage.add(entityWithFakeId)
        return entityWithFakeId.id
    }

    override suspend fun findSessionEntitiesByUserProgressionOrNull(userProgression: UserProgression): List<SessionEntity>? {
        return inMemoryStorage.filter { entity ->
            entity.extractUserProgression() == userProgression
        }.ifEmpty { null }
    }

    override fun findSessionEntitiesByUserProgression(userProgression: UserProgression): Flow<List<SessionEntity>> =
        flowOf(
            inMemoryStorage.filter { entity ->
                entity.extractUserProgression() == userProgression
            }
        )

    override suspend fun deleteSessionEntity(entity: SessionEntity) {
        inMemoryStorage.remove(entity)
    }

    override suspend fun deleteSessionEntitiesByMethodCycle(methodCycle: Int) {
        inMemoryStorage.removeAll { it.methodCycle == methodCycle }
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
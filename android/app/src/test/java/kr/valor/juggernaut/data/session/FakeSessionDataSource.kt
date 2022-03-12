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

    override suspend fun updateSessionEntity(entity: SessionEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun findSessionEntityById(id: Long): SessionEntity {
        return inMemoryStorage.find { it.id == id }!!
    }

    override suspend fun getLatestSessionEntityOrNull(): SessionEntity? {
        return inMemoryStorage.lastOrNull()
    }

    override fun getLatestSessionEntity(): Flow<SessionEntity> {
        return flowOf(inMemoryStorage.last())
    }

    override fun getAllSessionEntities(): Flow<List<SessionEntity>> {
        return flowOf(inMemoryStorage)
    }

    override suspend fun clear() {
        inMemoryStorage.clear()
    }
}
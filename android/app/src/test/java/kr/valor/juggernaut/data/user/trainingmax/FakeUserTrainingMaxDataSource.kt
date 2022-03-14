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

    private val inMemoryStorage = mutableListOf(
        UserTrainingMaxEntity(
            id = entityId++,
            methodCycle = 1,
            phaseName = Phase.REP10.name,
            liftCategoryName = LiftCategory.BENCHPRESS.name,
            trainingMaxWeights = 60,
            lastUpdatedAt = System.currentTimeMillis()
        ),
        UserTrainingMaxEntity(
            id = entityId++,
            methodCycle = 1,
            phaseName = Phase.REP10.name,
            liftCategoryName = LiftCategory.DEADLIFT.name,
            trainingMaxWeights = 120,
            lastUpdatedAt = System.currentTimeMillis()
        ),
        UserTrainingMaxEntity(
            id = entityId++,
            methodCycle = 1,
            phaseName = Phase.REP10.name,
            liftCategoryName = LiftCategory.SQUAT.name,
            trainingMaxWeights = 100,
            lastUpdatedAt = System.currentTimeMillis()
        ),
        UserTrainingMaxEntity(
            id = entityId++,
            methodCycle = 1,
            phaseName = Phase.REP10.name,
            liftCategoryName = LiftCategory.OVERHEADPRESS.name,
            trainingMaxWeights = 50,
            lastUpdatedAt = System.currentTimeMillis()
        )
    )

    override suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long {
        inMemoryStorage.add(entity)
        return entityId
    }

    override suspend fun findUserTrainingMaxEntitiesByUserProgression(userProgression: UserProgression): List<UserTrainingMaxEntity> {
        TODO()
    }

    override fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>> {
        return flowOf(inMemoryStorage)
    }

    override suspend fun clear() {
        inMemoryStorage.clear()
    }
}
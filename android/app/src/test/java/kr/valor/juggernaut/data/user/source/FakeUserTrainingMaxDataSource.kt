package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity

class FakeUserTrainingMaxDataSource: UserTrainingMaxDataSource {

    private var entityId: Long = 0L

    private val inMemoryStorage = mutableListOf(
        UserTrainingMaxEntity(
            id = entityId++,
            liftCategoryName = LiftCategory.BENCH_PRESS.name,
            trainingMaxWeights = 60,
            lastUpdatedAt = System.currentTimeMillis()
        ),
        UserTrainingMaxEntity(
            id = entityId++,
            liftCategoryName = LiftCategory.DEAD_LIFT.name,
            trainingMaxWeights = 120,
            lastUpdatedAt = System.currentTimeMillis()
        ),
        UserTrainingMaxEntity(
            id = entityId++,
            liftCategoryName = LiftCategory.SQUAT.name,
            trainingMaxWeights = 100,
            lastUpdatedAt = System.currentTimeMillis()
        ),
        UserTrainingMaxEntity(
            id = entityId++,
            liftCategoryName = LiftCategory.OVERHEAD_PRESS.name,
            trainingMaxWeights = 50,
            lastUpdatedAt = System.currentTimeMillis()
        )
    )

    override suspend fun getUserTrainingMaxEntityByLiftCategory(liftCategoryName: String): UserTrainingMaxEntity {
        return inMemoryStorage.findLast { it.liftCategoryName == liftCategoryName }!!
    }

    override suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long {
        inMemoryStorage.add(entity)
        return entityId
    }

    override fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>> {
        return flowOf(inMemoryStorage)
    }
}
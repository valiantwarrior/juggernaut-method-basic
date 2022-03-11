package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity

class FakeUserTrainingMaxDataSource: UserTrainingMaxDataSource {
    override suspend fun getUserTrainingMaxEntityByLiftCategory(liftCategoryName: String): UserTrainingMaxEntity {
        TODO("Not yet implemented")
    }

    override suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long {
        TODO("Not yet implemented")
    }

    override fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>> {
        TODO("Not yet implemented")
    }
}
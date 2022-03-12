package kr.valor.juggernaut.data.user.trainingmax.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.user.trainingmax.entity.UserTrainingMaxEntity

interface UserTrainingMaxDataSource {
    suspend fun findUserTrainingMaxEntityByLiftCategory(liftCategoryName: String): UserTrainingMaxEntity
    suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long
    fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>>
    suspend fun clear()
}
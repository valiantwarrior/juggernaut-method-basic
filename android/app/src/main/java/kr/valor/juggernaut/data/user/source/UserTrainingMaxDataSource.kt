package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity

interface UserTrainingMaxDataSource {
    suspend fun getUserTrainingMaxEntityByLiftCategory(liftCategory: LiftCategory): UserTrainingMaxEntity
    fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>>
    suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity)
}
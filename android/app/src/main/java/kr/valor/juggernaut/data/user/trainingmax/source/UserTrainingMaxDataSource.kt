package kr.valor.juggernaut.data.user.trainingmax.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.user.trainingmax.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.domain.user.model.UserProgression

interface UserTrainingMaxDataSource {

    suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long

    suspend fun findUserTrainingMaxEntitiesByUserProgression(userProgression: UserProgression): List<UserTrainingMaxEntity>

    fun getUserTrainingMaxEntities(): Flow<List<UserTrainingMaxEntity>>

    suspend fun clear()

}
package kr.valor.juggernaut.data.user

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.data.user.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.data.user.source.UserProgressionDataSource
import kr.valor.juggernaut.data.user.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import kr.valor.juggernaut.domain.user.repository.UserRepository

class DefaultUserRepository(
    private val userTrainingMaxMapper: UserTrainingMaxMapper,
    private val userTrainingMaxDataSource: UserTrainingMaxDataSource,
    private val userProgressionDataSource: UserProgressionDataSource
): UserRepository {

    override suspend fun getUserTrainingMax(liftCategory: LiftCategory): UserTrainingMax {
        val userTrainingMaxEntity =
            userTrainingMaxDataSource.getUserTrainingMaxEntityByLiftCategory(liftCategory.name)

        return userTrainingMaxMapper.map(userTrainingMaxEntity)
    }

    override suspend fun insertUserTrainingMaxEntity(entity: UserTrainingMaxEntity): Long =
        userTrainingMaxDataSource.insertUserTrainingMaxEntity(entity)

    override fun createUserTrainingMaxEntity(tmWeights: Int, liftCategory: LiftCategory): UserTrainingMaxEntity =
        UserTrainingMaxEntity(
            liftCategoryName = liftCategory.name,
            trainingMaxWeights = tmWeights,
            lastUpdatedAt = System.currentTimeMillis()
        )

    override fun getUserProgression(): Flow<UserProgression> =
        userProgressionDataSource.getUserProgressionData()

    override suspend fun updateUserProgression(microCycle: MicroCycle) {
        userProgressionDataSource.editUserProgression(microCycle)
    }

    override suspend fun updateUserProgression(methodCycle: MethodCycle) {
        userProgressionDataSource.editUserProgression(methodCycle)
    }

    override suspend fun updateUserProgression(phase: Phase) {
        userProgressionDataSource.editUserProgression(phase)
    }

    override suspend fun updateUserProgression(liftCategory: LiftCategory) {
        userProgressionDataSource.editUserProgression(liftCategory)
    }

    override suspend fun clearUserTrainingMax() {
        userTrainingMaxDataSource.clear()
    }

    override suspend fun clearUserProgression() {
        userProgressionDataSource.clear()
    }
}
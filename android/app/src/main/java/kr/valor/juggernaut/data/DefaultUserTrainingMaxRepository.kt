package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.user.trainingmax.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.data.user.trainingmax.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.data.user.trainingmax.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import kr.valor.juggernaut.domain.user.repository.UserTrainingMaxRepository

class DefaultUserTrainingMaxRepository(
    private val userTrainingMaxMapper: UserTrainingMaxMapper,
    private val userTrainingMaxDataSource: UserTrainingMaxDataSource,
    private val weightUnitTransformer: WeightUnitTransformer
): UserTrainingMaxRepository {

    private val transform: (Double) -> Int = weightUnitTransformer::transform

    private val toDatabaseModel: UserTrainingMax.() -> UserTrainingMaxEntity = {
        userTrainingMaxMapper.mapModel(this)
    }

    private val toDomainModel: UserTrainingMaxEntity.() -> UserTrainingMax = {
        userTrainingMaxMapper.mapEntity(this)
    }

    override fun getAllUserTrainingMaxes(): Flow<List<UserTrainingMax>> =
        userTrainingMaxDataSource.getUserTrainingMaxEntities().map { entities ->
            entities.map(toDomainModel)
        }

    override suspend fun findUserTrainingMaxesByUserProgression(userProgression: UserProgression): List<UserTrainingMax> =
        userTrainingMaxDataSource.findUserTrainingMaxEntitiesByUserProgression(userProgression)
            .map(toDomainModel)

    override suspend fun insertUserTrainingMax(trainingMax: UserTrainingMax): Long =
        userTrainingMaxDataSource.insertUserTrainingMaxEntity(trainingMax.toDatabaseModel())

    override fun createUserTrainingMax(liftCategory: LiftCategory, inputWeights: Double, userProgression: UserProgression): UserTrainingMax =
        UserTrainingMax(
            methodCycle = userProgression.methodCycle,
            phase = userProgression.phase,
            liftCategory = liftCategory,
            trainingMaxWeights = transform(inputWeights),
            lastUpdatedAt = System.currentTimeMillis()
        )

    override suspend fun clear() {
        userTrainingMaxDataSource.clear()
    }

}
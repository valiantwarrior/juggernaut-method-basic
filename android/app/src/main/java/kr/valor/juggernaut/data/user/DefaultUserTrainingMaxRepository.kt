package kr.valor.juggernaut.data.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.user.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.data.user.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import kr.valor.juggernaut.domain.user.repository.UserTrainingMaxRepository

class DefaultUserTrainingMaxRepository(
    private val userTrainingMaxMapper: UserTrainingMaxMapper,
    private val userTrainingMaxDataSource: UserTrainingMaxDataSource,
    private val weightUnitTransformer: WeightUnitTransformer
): UserTrainingMaxRepository {

    override fun getAllUserTrainingMaxes(): Flow<List<UserTrainingMax>> =
        userTrainingMaxDataSource.getUserTrainingMaxEntities().map { entities ->
            userTrainingMaxMapper.map(entities)
        }

    override suspend fun findUserTrainingMaxByLiftCategory(liftCategory: LiftCategory): UserTrainingMax =
        userTrainingMaxDataSource
            .findUserTrainingMaxEntityByLiftCategory(liftCategory.name)
            .let { entity ->
                userTrainingMaxMapper.map(entity)
            }

    override suspend fun insertUserTrainingMax(trainingMax: UserTrainingMax): Long =
        userTrainingMaxMapper.map(trainingMax).let { entity ->
            userTrainingMaxDataSource.insertUserTrainingMaxEntity(entity)
        }

    override fun createUserTrainingMax(rawTmWeights: Double, liftCategory: LiftCategory): UserTrainingMax =
        UserTrainingMax(
            liftCategory = liftCategory,
            trainingMaxWeights = weightUnitTransformer.transform(rawTmWeights),
            lastUpdatedAt = System.currentTimeMillis()
        )

    override suspend fun clear() {
        userTrainingMaxDataSource.clear()
    }
}
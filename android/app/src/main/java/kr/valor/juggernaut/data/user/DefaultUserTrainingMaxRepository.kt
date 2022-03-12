package kr.valor.juggernaut.data.user

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.common.converter.WeightUnitConversionDelegate
import kr.valor.juggernaut.data.user.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.data.user.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import kr.valor.juggernaut.domain.user.repository.UserTrainingMaxRepository

class DefaultUserTrainingMaxRepository(
    private val userTrainingMaxMapper: UserTrainingMaxMapper,
    private val userTrainingMaxDataSource: UserTrainingMaxDataSource,
    weightUnitConversionDelegate: WeightUnitConversionDelegate
): UserTrainingMaxRepository, WeightUnitConversionDelegate by weightUnitConversionDelegate {

    override suspend fun getUserTrainingMaxByLiftCategory(liftCategory: LiftCategory): UserTrainingMax =
        userTrainingMaxDataSource
            .getUserTrainingMaxEntityByLiftCategory(liftCategory.name)
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
            trainingMaxWeights = convert(rawTmWeights),
            lastUpdatedAt = System.currentTimeMillis()
        )

    override suspend fun clear() {
        userTrainingMaxDataSource.clear()
    }
}
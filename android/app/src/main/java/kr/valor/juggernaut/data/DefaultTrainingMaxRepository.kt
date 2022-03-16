package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.data.trainingmax.mapper.TrainingMaxMapper
import kr.valor.juggernaut.data.trainingmax.source.TrainingMaxDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository

class DefaultTrainingMaxRepository(
    private val trainingMaxMapper: TrainingMaxMapper,
    private val trainingMaxDataSource: TrainingMaxDataSource,
    private val weightUnitTransformer: WeightUnitTransformer
): TrainingMaxRepository {

    private val transform: (Double) -> Int = weightUnitTransformer::transform

    private val toDatabaseModel: TrainingMax.() -> TrainingMaxEntity = {
        trainingMaxMapper.mapModel(this)
    }

    private val toDomainModel: TrainingMaxEntity.() -> TrainingMax = {
        trainingMaxMapper.mapEntity(this)
    }

    override fun getAllUserTrainingMaxes(): Flow<List<TrainingMax>> =
        trainingMaxDataSource.getUserTrainingMaxEntities().map { entities ->
            entities.map(toDomainModel)
        }

    override suspend fun findUserTrainingMaxesByUserProgression(userProgression: UserProgression): List<TrainingMax> =
        trainingMaxDataSource.findUserTrainingMaxEntitiesByUserProgression(userProgression)
            .map(toDomainModel)

    override suspend fun deleteUserTrainingMaxesByMethodCycle(methodCycle: MethodCycle) {
        trainingMaxDataSource.deleteUserTrainingMaxesByMethodCycle(methodCycle.value)
    }

    override suspend fun insertUserTrainingMax(trainingMax: TrainingMax): Long =
        trainingMaxDataSource.insertUserTrainingMaxEntity(trainingMax.toDatabaseModel())

    override fun createUserTrainingMax(liftCategory: LiftCategory, inputWeights: Double, userProgression: UserProgression): TrainingMax =
        TrainingMax(
            methodCycle = userProgression.methodCycle,
            phase = userProgression.phase,
            liftCategory = liftCategory,
            trainingMaxWeights = transform(inputWeights),
            lastUpdatedAt = System.currentTimeMillis()
        )

    override suspend fun clear() {
        trainingMaxDataSource.clear()
    }

}
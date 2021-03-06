package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.common.di.KgWeightUnit
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.data.trainingmax.mapper.TrainingMaxMapper
import kr.valor.juggernaut.data.trainingmax.source.TrainingMaxDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.CorrespondingBaseRecord
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultTrainingMaxRepository @Inject constructor(
    private val trainingMaxMapper: TrainingMaxMapper,
    private val trainingMaxDataSource: TrainingMaxDataSource
): TrainingMaxRepository {

    override fun getAllTrainingMaxes(): Flow<List<TrainingMax>> =
        trainingMaxDataSource.getAllTrainingMaxEntities().map { entities ->
            entities.toDomainModels()
        }

    override suspend fun findTrainingMaxesByUserProgression(userProgression: UserProgression): List<TrainingMax> =
        with(userProgression) {
            trainingMaxDataSource.findTrainingMaxEntitiesByMethodCycleAndPhase(methodCycle.value, phase.name)
                .toDomainModels()
        }

    override suspend fun deleteTrainingMaxesByMethodCycle(methodCycle: MethodCycle) {
        trainingMaxDataSource.deleteTrainingMaxesByMethodCycle(methodCycle.value)
    }

    override suspend fun insertTrainingMax(trainingMax: TrainingMax): Long =
        trainingMaxDataSource.insertTrainingMaxEntity(trainingMax.toDatabaseModel())

    override fun createTrainingMax(
        liftCategory: LiftCategory,
        tmWeightsWithCorrespondingBaseRecordPair: Pair<Int, CorrespondingBaseRecord>,
        methodCycleWithPhasePair: Pair<MethodCycle, Phase>
    ): TrainingMax {
        val (methodCycle, phase) = methodCycleWithPhasePair
        val (trainingMaxWeights, correspondingBaseRecord) = tmWeightsWithCorrespondingBaseRecordPair

        return TrainingMax(
            methodCycle = methodCycle,
            phase = phase,
            liftCategory = liftCategory,
            trainingMaxWeights = trainingMaxWeights,
            correspondingBaseRecord = correspondingBaseRecord,
            lastUpdatedAt = System.currentTimeMillis()
        )
    }

    override suspend fun clear() {
        trainingMaxDataSource.clear()
    }

    private fun TrainingMax.toDatabaseModel(): TrainingMaxEntity =
        trainingMaxMapper.mapModel(this)

    private fun TrainingMaxEntity.toDomainModel(): TrainingMax =
        trainingMaxMapper.mapEntity(this)

    private fun List<TrainingMaxEntity>.toDomainModels(): List<TrainingMax> =
        map { entity -> entity.toDomainModel() }

}
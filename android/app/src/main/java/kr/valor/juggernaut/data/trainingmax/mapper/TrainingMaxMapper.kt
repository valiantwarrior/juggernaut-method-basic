package kr.valor.juggernaut.data.trainingmax.mapper

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.mapper.EntityMapper
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import javax.inject.Inject

interface TrainingMaxMapper: EntityMapper<TrainingMaxEntity, TrainingMax> {

    fun mapModel(model: TrainingMax): TrainingMaxEntity

}

class DefaultTrainingMaxMapper @Inject constructor(): TrainingMaxMapper {

    override fun mapEntity(entity: TrainingMaxEntity): TrainingMax =
        TrainingMax(
            id = entity.id,
            methodCycle = MethodCycle(entity.methodCycleValue),
            phase = Phase.valueOf(entity.phaseName),
            liftCategory = LiftCategory.valueOf(entity.liftCategoryName),
            trainingMaxWeights = entity.trainingMaxWeights,
            lastUpdatedAt = entity.lastUpdatedAt
        )

    override fun mapModel(model: TrainingMax): TrainingMaxEntity =
        with(model) {
            TrainingMaxEntity(
                methodCycleValue = methodCycle.value,
                phaseName = phase.name,
                liftCategoryName = liftCategory.name,
                trainingMaxWeights = trainingMaxWeights,
                lastUpdatedAt = lastUpdatedAt
            )
        }

}
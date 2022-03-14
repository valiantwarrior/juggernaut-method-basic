package kr.valor.juggernaut.data.user.trainingmax.mapper

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.mapper.EntityMapper
import kr.valor.juggernaut.data.user.trainingmax.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface UserTrainingMaxMapper: EntityMapper<UserTrainingMaxEntity, UserTrainingMax> {

    fun mapModel(model: UserTrainingMax): UserTrainingMaxEntity

}

class DefaultUserTrainingMaxMapper: UserTrainingMaxMapper {

    override fun mapEntity(entity: UserTrainingMaxEntity): UserTrainingMax =
        UserTrainingMax(
            id = entity.id,
            methodCycle = MethodCycle(entity.methodCycle),
            phase = Phase.valueOf(entity.phaseName),
            liftCategory = LiftCategory.valueOf(entity.liftCategoryName),
            trainingMaxWeights = entity.trainingMaxWeights,
            lastUpdatedAt = entity.lastUpdatedAt
        )

    override fun mapModel(model: UserTrainingMax): UserTrainingMaxEntity =
        with(model) {
            UserTrainingMaxEntity(
                methodCycle = methodCycle.value,
                phaseName = phase.name,
                liftCategoryName = liftCategory.name,
                trainingMaxWeights = trainingMaxWeights,
                lastUpdatedAt = lastUpdatedAt
            )
        }

}
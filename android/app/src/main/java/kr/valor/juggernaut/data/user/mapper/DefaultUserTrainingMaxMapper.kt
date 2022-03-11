package kr.valor.juggernaut.data.user.mapper

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.common.EntityMapper
import kr.valor.juggernaut.data.user.entity.UserTrainingMaxEntity
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface UserTrainingMaxMapper: EntityMapper<UserTrainingMaxEntity, UserTrainingMax> {
    fun map(model: UserTrainingMax): UserTrainingMaxEntity
}

class DefaultUserTrainingMaxMapper: UserTrainingMaxMapper {
    override fun map(entity: UserTrainingMaxEntity): UserTrainingMax =
        UserTrainingMax(
            id = entity.id,
            liftCategory = LiftCategory.valueOf(entity.liftCategoryName),
            trainingMaxWeights = entity.trainingMaxWeights,
            lastUpdatedAt = entity.lastUpdatedAt
        )

    override fun map(model: UserTrainingMax): UserTrainingMaxEntity {
        TODO("Not yet implemented")
    }
}
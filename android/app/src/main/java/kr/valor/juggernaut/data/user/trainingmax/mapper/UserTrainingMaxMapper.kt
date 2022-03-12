package kr.valor.juggernaut.data.user.mapper

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.data.common.mapper.EntityMapper
import kr.valor.juggernaut.data.user.trainingmax.entity.UserTrainingMaxEntity
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

    override fun map(entities: List<UserTrainingMaxEntity>): List<UserTrainingMax> =
        entities.map { map(it) }

    override fun map(model: UserTrainingMax): UserTrainingMaxEntity {
        TODO("Not yet implemented")
    }
}
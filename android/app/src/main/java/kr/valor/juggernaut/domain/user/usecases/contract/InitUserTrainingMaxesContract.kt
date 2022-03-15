package kr.valor.juggernaut.domain.user.usecases.contract

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.user.usecases.usecase.InitUserTrainingMaxUseCase

interface InitUserTrainingMaxesContract {

    suspend operator fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Double>)

}

class InitUserTrainingMaxesContractImpl(
    private val initUserTrainingMaxUseCase: InitUserTrainingMaxUseCase
): InitUserTrainingMaxesContract {

    override suspend fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Double>) {
        if (liftCategoryWeightsMap.size != LiftCategory.TOTAL_LIFT_CATEGORY_COUNT) {
            throw IllegalArgumentException()
        }

        liftCategoryWeightsMap.forEach { (liftCategory, weights) ->
            initUserTrainingMaxUseCase(liftCategory, weights)
        }
    }

}

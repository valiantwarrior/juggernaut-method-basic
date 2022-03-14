package kr.valor.juggernaut.domain.user.contract

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.repository.UserTrainingMaxRepository
import kr.valor.juggernaut.domain.user.usecase.GetUserProgressionUseCase

interface InitializeUserTrainingMaxContract {

    suspend operator fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Double>)

    suspend operator fun invoke(liftCategoryWeightsPair: Pair<LiftCategory, Double>)

}

class InitializeUserTrainingMaxContractImpl(
    private val userTrainingMaxRepository: UserTrainingMaxRepository,
    private val getUserProgressionUseCase: GetUserProgressionUseCase
): InitializeUserTrainingMaxContract {

    override suspend fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Double>) {
        if (liftCategoryWeightsMap.size != LiftCategory.TOTAL_LIFT_CATEGORY_COUNT) {
            throw IllegalArgumentException()
        }

        val userProgression = getUserProgressionUseCase().first()

        liftCategoryWeightsMap.forEach { (liftCategory, weights) ->
            createAndInsertUserTrainingMax(liftCategory, weights, userProgression)
        }
    }

    override suspend fun invoke(liftCategoryWeightsPair: Pair<LiftCategory, Double>) {
        val (liftCategory, weights) = liftCategoryWeightsPair
        val userProgression = getUserProgressionUseCase().first()

        createAndInsertUserTrainingMax(liftCategory, weights, userProgression)
    }

    private suspend fun createAndInsertUserTrainingMax(liftCategory: LiftCategory, weights: Double, userProgression: UserProgression) {
        with(userTrainingMaxRepository) {
            createUserTrainingMax(liftCategory, weights, userProgression).also {
                insertUserTrainingMax(it)
            }
        }
    }

}

package kr.valor.juggernaut.domain.user.usecases.usecase

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.user.repository.UserTrainingMaxRepository

interface InitUserTrainingMaxUseCase {

    suspend operator fun invoke(liftCategory: LiftCategory, weights: Double)

}

class InitUserTrainingMaxUseCaseImpl(
    private val getUserProgressionUseCase: GetUserProgressionUseCase,
    private val repository: UserTrainingMaxRepository
): InitUserTrainingMaxUseCase {

    override suspend fun invoke(liftCategory: LiftCategory, weights: Double) {
        with(repository) {
            val userProgression = getUserProgressionUseCase().first()
            createUserTrainingMax(liftCategory, weights, userProgression).also {
                insertUserTrainingMax(it)
            }
        }
    }

}
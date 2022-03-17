package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository

interface InitializeUserTrainingMaxUseCase {

    suspend operator fun invoke(liftCategory: LiftCategory, inputWeights: Double, userProgression: UserProgression)

}

class InitializeUserTrainingMaxUseCaseImpl(
    private val repository: TrainingMaxRepository
): InitializeUserTrainingMaxUseCase {

    override suspend fun invoke(liftCategory: LiftCategory, inputWeights: Double, userProgression: UserProgression) {
        val newTrainingMax = repository.createTrainingMax(liftCategory, inputWeights, userProgression)
        repository.insertTrainingMax(newTrainingMax)
    }
}
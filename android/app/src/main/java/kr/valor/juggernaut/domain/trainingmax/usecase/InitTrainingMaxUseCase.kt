package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject

class InitTrainingMaxUseCase @Inject constructor(
    private val repository: TrainingMaxRepository
) {

    suspend operator fun invoke(liftCategory: LiftCategory, inputWeights: Int, userProgression: UserProgression) {
        val newTrainingMax = repository.createTrainingMax(liftCategory, inputWeights, userProgression)
        repository.insertTrainingMax(newTrainingMax)
    }

}
package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject

class FindTrainingMaxesUseCase @Inject constructor(
    private val repository: TrainingMaxRepository
) {
    suspend operator fun invoke(userProgression: UserProgression): List<TrainingMax> =
        repository.findTrainingMaxesByUserProgression(userProgression)
}
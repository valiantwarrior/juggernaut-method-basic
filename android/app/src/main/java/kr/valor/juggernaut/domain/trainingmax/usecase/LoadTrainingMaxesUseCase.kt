package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject

class LoadTrainingMaxesUseCase @Inject constructor(
    private val repository: TrainingMaxRepository
) {

    operator fun invoke() = repository.getAllTrainingMaxes()

}
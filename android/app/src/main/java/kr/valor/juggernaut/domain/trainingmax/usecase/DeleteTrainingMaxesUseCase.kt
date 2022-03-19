package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject

class DeleteTrainingMaxesUseCase @Inject constructor(
    private val repository: TrainingMaxRepository
) {

    suspend operator fun invoke(methodCycle: MethodCycle) {
        repository.deleteTrainingMaxesByMethodCycle(methodCycle)
    }

}
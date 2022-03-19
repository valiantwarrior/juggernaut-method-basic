package kr.valor.juggernaut.domain.progression.usecase.usecase

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject

class UpdateMethodCycleStateUseCase @Inject constructor(
    private val repository: ProgressionStateRepository
) {

    suspend operator fun invoke(methodCycle: MethodCycle) {
        repository.updateMethodCycleState(methodCycle)
    }

}
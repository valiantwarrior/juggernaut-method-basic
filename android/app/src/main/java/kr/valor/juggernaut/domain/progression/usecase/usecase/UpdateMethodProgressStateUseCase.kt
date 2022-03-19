package kr.valor.juggernaut.domain.progression.usecase.usecase

import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject

class UpdateMethodProgressStateUseCase @Inject constructor(
    private val repository: ProgressionStateRepository
) {
    suspend operator fun invoke(methodProgressState: MethodProgressState) {
        repository.updateMethodProgressState(methodProgressState)
    }
}
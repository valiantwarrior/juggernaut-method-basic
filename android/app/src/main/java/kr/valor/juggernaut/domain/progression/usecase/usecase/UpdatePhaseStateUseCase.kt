package kr.valor.juggernaut.domain.progression.usecase.usecase

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject

class UpdatePhaseStateUseCase @Inject constructor(
    private val repository: ProgressionStateRepository
) {

    suspend operator fun invoke(phase: Phase) {
        repository.updatePhaseState(phase)
    }

}
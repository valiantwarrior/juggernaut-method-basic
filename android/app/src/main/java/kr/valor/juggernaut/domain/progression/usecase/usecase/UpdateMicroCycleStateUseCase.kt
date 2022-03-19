package kr.valor.juggernaut.domain.progression.usecase.usecase

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject

class UpdateMicroCycleStateUseCase @Inject constructor(
    private val repository: ProgressionStateRepository
) {

    suspend operator fun invoke(microCycle: MicroCycle) {
        repository.updateMicroCycleState(microCycle)
    }

}
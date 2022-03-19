package kr.valor.juggernaut.domain.progression.usecase.contract

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMicroCycleStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdatePhaseStateUseCase
import javax.inject.Inject

class UpdateUserProgressionPhaseContract @Inject constructor(
    private val updatePhaseStateUseCase: UpdatePhaseStateUseCase,
    private val updateMicroCycleStateUseCase: UpdateMicroCycleStateUseCase
) {

    suspend operator fun invoke(phase: Phase) {
        updatePhaseStateUseCase(phase)
        updateMicroCycleStateUseCase(MicroCycle.INITIAL)
    }

}
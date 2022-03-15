package kr.valor.juggernaut.domain.user.usecases.contract

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdateMicroCycleStateUseCase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdatePhaseStateUseCase

interface UpdateUserProgressionPhaseContract {

    suspend operator fun invoke(phase: Phase)

}

class UpdateUserProgressionPhaseContractImpl(
    private val updatePhaseStateUseCase: UpdatePhaseStateUseCase,
    private val updateMicroCycleStateUseCase: UpdateMicroCycleStateUseCase
): UpdateUserProgressionPhaseContract {

    override suspend fun invoke(phase: Phase) {
        updatePhaseStateUseCase(phase)
        updateMicroCycleStateUseCase(MicroCycle.INITIAL)
    }

}
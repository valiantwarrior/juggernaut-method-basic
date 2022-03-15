package kr.valor.juggernaut.domain.user.usecases.contract

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdateUserProgressionUseCase

interface UpdateUserProgressionPhaseContract {

    suspend operator fun invoke(phase: Phase)

}

class UpdateUserProgressionPhaseContractImpl(
    private val updateUserProgressionUseCase: UpdateUserProgressionUseCase
): UpdateUserProgressionPhaseContract {

    override suspend fun invoke(phase: Phase) {
        updateUserProgressionUseCase(phase)
        updateUserProgressionUseCase(MicroCycle.ACCUMULATION)
    }

}
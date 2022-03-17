package kr.valor.juggernaut.domain.common.contract

import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMethodProgressStateUseCase

interface CompleteMethodSetupContract {

    suspend operator fun invoke()

}

class CompleteMethodSetupContractImpl(
    private val updateMethodProgressStateUseCase: UpdateMethodProgressStateUseCase
): CompleteMethodSetupContract {

    override suspend fun invoke() {
        updateMethodProgressStateUseCase(MethodProgressState.DONE)
    }

}
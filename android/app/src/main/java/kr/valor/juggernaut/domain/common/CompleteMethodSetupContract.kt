package kr.valor.juggernaut.domain.common

import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMethodProgressStateUseCase
import javax.inject.Inject

class CompleteMethodSetupContract @Inject constructor(
    private val updateMethodProgressStateUseCase: UpdateMethodProgressStateUseCase
) {

    suspend operator fun invoke() {
        updateMethodProgressStateUseCase(MethodProgressState.DONE)
    }

}
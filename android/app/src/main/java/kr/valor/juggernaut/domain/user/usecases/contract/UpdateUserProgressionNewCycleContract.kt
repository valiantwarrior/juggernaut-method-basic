package kr.valor.juggernaut.domain.user.usecases.contract

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdateMethodCycleStateUseCase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdateMicroCycleStateUseCase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdatePhaseStateUseCase

interface UpdateUserProgressionNewCycleContract {

    suspend operator fun invoke(nextMethodCycle: MethodCycle)

}

class UpdateUserProgressionNewCycleContractImpl(
    private val updateMethodCycleStateUseCase: UpdateMethodCycleStateUseCase,
    private val updatePhaseStateUseCase: UpdatePhaseStateUseCase,
    private val updateMicroCycleStateUseCase: UpdateMicroCycleStateUseCase
): UpdateUserProgressionNewCycleContract {

    override suspend fun invoke(newCycle: MethodCycle) {
        updateMethodCycleStateUseCase(newCycle)
        updatePhaseStateUseCase(Phase.INITIAL)
        updateMicroCycleStateUseCase(MicroCycle.INITIAL)
    }

}
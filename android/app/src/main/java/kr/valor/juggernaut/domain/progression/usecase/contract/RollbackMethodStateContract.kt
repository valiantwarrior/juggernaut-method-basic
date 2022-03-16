package kr.valor.juggernaut.domain.progression.usecase.contract

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMethodCycleStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMicroCycleStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdatePhaseStateUseCase

interface RollbackMethodStateContract {

    suspend operator fun invoke(checkpoint: MethodCycle)

}

class RollbackMethodStateContractImpl(
    private val updateMethodCycleStateUseCase: UpdateMethodCycleStateUseCase,
    private val updatePhaseStateUseCase: UpdatePhaseStateUseCase,
    private val updateMicroCycleStateUseCase: UpdateMicroCycleStateUseCase
): RollbackMethodStateContract {

    override suspend fun invoke(checkpoint: MethodCycle) {
        updateMethodCycleStateUseCase(checkpoint)
        updatePhaseStateUseCase(Phase.FINAL)
        updateMicroCycleStateUseCase(MicroCycle.FINAL)
    }
}
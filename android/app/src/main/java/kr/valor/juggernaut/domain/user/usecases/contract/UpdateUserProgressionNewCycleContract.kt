package kr.valor.juggernaut.domain.user.usecases.contract

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.usecases.usecase.UpdateUserProgressionUseCase

interface UpdateUserProgressionNewCycleContract {

    suspend operator fun invoke(nextMethodCycle: MethodCycle)

}

class UpdateUserProgressionNewCycleContractImpl(
    private val updateUserProgressionUseCase: UpdateUserProgressionUseCase
): UpdateUserProgressionNewCycleContract {

    override suspend fun invoke(newCycle: MethodCycle) {
        updateUserProgressionUseCase(newCycle)
        updateUserProgressionUseCase(Phase.REP10)
        updateUserProgressionUseCase(MicroCycle.ACCUMULATION)
    }

}
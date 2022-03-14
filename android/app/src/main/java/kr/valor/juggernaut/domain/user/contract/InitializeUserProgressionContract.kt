package kr.valor.juggernaut.domain.user.contract

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle.ACCUMULATION
import kr.valor.juggernaut.common.Phase.REP10
import kr.valor.juggernaut.domain.user.usecase.UpdateUserProgressionUseCase

interface InitializeUserProgressionContract {

    suspend operator fun invoke(nextMethodCycle: MethodCycle)

}

class InitializeUserProgressionContractImpl(
    private val updateUserProgressionUseCase: UpdateUserProgressionUseCase
): InitializeUserProgressionContract {

    override suspend fun invoke(nextMethodCycle: MethodCycle) {
        updateUserProgressionUseCase(nextMethodCycle)
        updateUserProgressionUseCase(REP10)
        updateUserProgressionUseCase(ACCUMULATION)
    }
}
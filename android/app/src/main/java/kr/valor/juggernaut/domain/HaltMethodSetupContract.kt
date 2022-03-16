package kr.valor.juggernaut.domain

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodCycle.Companion.minus
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.domain.session.usecase.usecase.DeleteSessionsByMethodCycleUseCase
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.IllegalProgressionStateSetupException.Companion.HALT_ERROR_MESSAGE
import kr.valor.juggernaut.domain.progression.usecase.contract.RollbackMethodStateContract
import kr.valor.juggernaut.domain.progression.usecase.usecase.ClearProgressionStateUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.DeleteUserTrainingMaxesByMethodCycleUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.GetProgressionStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMethodProgressStateUseCase

enum class HaltBehavior {
    DELETE_ALL_ENTITIES, PRESERVE
}

interface HaltMethodStateContract {

    suspend operator fun invoke(haltBehavior: HaltBehavior = HaltBehavior.DELETE_ALL_ENTITIES)

}

class HaltMethodStateContractImpl(
    private val getProgressionStateUseCase: GetProgressionStateUseCase,
    private val updateMethodProgressStateUseCase: UpdateMethodProgressStateUseCase,
    private val clearProgressionStateUseCase: ClearProgressionStateUseCase,
    private val rollbackMethodStateContract: RollbackMethodStateContract,
    private val deleteUserTrainingMaxesByMethodCycleUseCase: DeleteUserTrainingMaxesByMethodCycleUseCase,
    private val deleteSessionsByMethodCycleUseCase: DeleteSessionsByMethodCycleUseCase
): HaltMethodStateContract {

    override suspend fun invoke(haltBehavior: HaltBehavior) {
        when(val currentProgressionState = getProgressionStateUseCase().first()) {
            is ProgressionState.None -> {
                throw IllegalProgressionStateSetupException(HALT_ERROR_MESSAGE)
            }
            is ProgressionState.OnGoing -> {
                val currentMethodCycle = currentProgressionState.currentUserProgression.methodCycle

                deleteUserTrainingMaxesByMethodCycleUseCase(currentMethodCycle)
                if (haltBehavior == HaltBehavior.DELETE_ALL_ENTITIES) {
                    deleteSessionsByMethodCycleUseCase(currentMethodCycle)
                }

                if (currentMethodCycle == MethodCycle.INITIAL) {
                    clearProgressionStateUseCase()
                    return
                }

                updateMethodProgressStateUseCase(MethodProgressState.DONE)
                rollbackMethodStateContract(currentMethodCycle - 1)
            }
            is ProgressionState.Done -> {
                throw IllegalProgressionStateSetupException(HALT_ERROR_MESSAGE)
            }
        }
    }

}

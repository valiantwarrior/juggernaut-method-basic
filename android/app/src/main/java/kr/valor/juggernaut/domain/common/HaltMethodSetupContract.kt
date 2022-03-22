package kr.valor.juggernaut.domain.common

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodCycle.Companion.minus
import kr.valor.juggernaut.common.MethodCycle.Companion.plus
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.session.usecase.usecase.DeleteSessionsUseCase
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.common.IllegalProgressionStateSetupException.Companion.HALT_ERROR_MESSAGE
import kr.valor.juggernaut.domain.progression.usecase.contract.RollbackMethodStateContract
import kr.valor.juggernaut.domain.progression.usecase.usecase.ClearProgressionStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMethodProgressStateUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.DeleteTrainingMaxesUseCase
import javax.inject.Inject

class HaltMethodStateContract @Inject constructor(
    private val loadProgressionStateUseCase: LoadProgressionStateUseCase,
    private val updateMethodProgressStateUseCase: UpdateMethodProgressStateUseCase,
    private val clearProgressionStateUseCase: ClearProgressionStateUseCase,
    private val rollbackMethodStateContract: RollbackMethodStateContract,
    private val deleteTrainingMaxesUseCase: DeleteTrainingMaxesUseCase,
    private val deleteSessionsUseCase: DeleteSessionsUseCase
) {

    enum class HaltBehavior {
        DELETE_ALL_ENTITIES, PRESERVE
    }

    suspend operator fun invoke(haltBehavior: HaltBehavior = HaltBehavior.DELETE_ALL_ENTITIES) {
        when(val currentProgressionState = loadProgressionStateUseCase().first()) {
            is ProgressionState.None -> {
                throw IllegalProgressionStateSetupException(HALT_ERROR_MESSAGE)
            }
            is ProgressionState.OnGoing -> {
                val currentMethodCycle = currentProgressionState.currentUserProgression.methodCycle
                val currentPhase = currentProgressionState.currentUserProgression.phase

                deleteTrainingMaxesUseCase(currentMethodCycle)
                if (currentPhase == Phase.FINAL) {
                    /**
                     * When user attempt to halt method at Realization phase,
                     * Maybe, there are training maxes generated after Realization phase
                     * A MethodCycle for those things are set to next method cycle,
                     * So delete those things.
                     * See [InitNextPhaseTrainingMaxUseCase]
                     */
                    deleteTrainingMaxesUseCase(currentMethodCycle + 1)
                }

                if (haltBehavior == HaltBehavior.DELETE_ALL_ENTITIES) {
                    deleteSessionsUseCase(currentMethodCycle)
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
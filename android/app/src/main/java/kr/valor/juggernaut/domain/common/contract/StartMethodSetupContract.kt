package kr.valor.juggernaut.domain.common.contract

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodCycle.Companion.plus
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.common.contract.IllegalProgressionStateSetupException.Companion.START_ERROR_MESSAGE
import kr.valor.juggernaut.domain.progression.usecase.contract.UpdateUserProgressionNewCycleContract
import kr.valor.juggernaut.domain.progression.usecase.usecase.GetProgressionStateUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.InitializeUserTrainingMaxUseCase

interface StartMethodSetupContract {

    suspend operator fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Double>)

}

class StartMethodSetupContractImpl(
    private val getProgressionStateUseCase: GetProgressionStateUseCase,
    private val initializeUserTrainingMaxUseCase: InitializeUserTrainingMaxUseCase,
    private val updateUserProgressionNewCycleContract: UpdateUserProgressionNewCycleContract
): StartMethodSetupContract {

    override suspend fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Double>) {
        when(val previousProgressionState = getProgressionStateUseCase().first()) {
            is ProgressionState.None -> {
                updateUserProgressionNewCycleContract(MethodCycle.INITIAL)
                initializeUserTrainingMaxByUpdatedUserProgression(liftCategoryWeightsMap)
            }
            is ProgressionState.Done -> {
                val previousMethodCycleState = previousProgressionState.latestUserProgression.methodCycle
                updateUserProgressionNewCycleContract(previousMethodCycleState + 1)
                initializeUserTrainingMaxByUpdatedUserProgression(liftCategoryWeightsMap)
            }
            is ProgressionState.OnGoing -> {
                throw IllegalProgressionStateSetupException(START_ERROR_MESSAGE)
            }
        }
    }

    private suspend fun initializeUserTrainingMaxByUpdatedUserProgression(liftCategoryWeightsMap: Map<LiftCategory, Double>) {
        val userProgressionAfterUpdatedInThisContext = getUserProgressionAfterUpdated()
        liftCategoryWeightsMap.forEach { (liftCategory, inputWeights) ->
            initializeUserTrainingMaxUseCase(liftCategory, inputWeights, userProgressionAfterUpdatedInThisContext)
        }
    }

    private suspend fun getUserProgressionAfterUpdated(): UserProgression =
        (getProgressionStateUseCase().first() as ProgressionState.OnGoing).currentUserProgression

}

class IllegalProgressionStateSetupException(override val message: String): Exception(message) {
    companion object {
        const val START_ERROR_MESSAGE = "A new method cannot be started if the current state is ProgressionState.OnGoing."
        const val HALT_ERROR_MESSAGE = "Cannot halt current method because there is no method currently in progress."
    }
}
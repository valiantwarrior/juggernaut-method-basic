package kr.valor.juggernaut.domain.common

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodCycle.Companion.plus
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.common.IllegalProgressionStateSetupException.Companion.START_ERROR_MESSAGE
import kr.valor.juggernaut.domain.progression.usecase.contract.UpdateUserProgressionNewCycleContract
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMethodProgressStateUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.DeleteTrainingMaxesUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.InitTrainingMaxUseCase
import javax.inject.Inject

class StartMethodSetupContract @Inject constructor(
    private val loadProgressionStateUseCase: LoadProgressionStateUseCase,
    private val deleteTrainingMaxesUseCase: DeleteTrainingMaxesUseCase,
    private val initTrainingMaxUseCase: InitTrainingMaxUseCase,
    private val updateUserProgressionNewCycleContract: UpdateUserProgressionNewCycleContract,
    private val updateMethodProgressStateUseCase: UpdateMethodProgressStateUseCase
) {

    suspend operator fun invoke(liftCategoryWeightsMap: Map<LiftCategory, Int>?, usingPreviousRecord: Boolean = false) {
        when(val previousProgressionState = loadProgressionStateUseCase().first()) {
            is ProgressionState.None -> {
                updateMethodProgressStateUseCase(MethodProgressState.ONGOING)
                updateUserProgressionNewCycleContract(MethodCycle.INITIAL)
                initializeUserTrainingMaxByUpdatedUserProgression(liftCategoryWeightsMap!!)
            }
            is ProgressionState.Done -> {
                val nextMethodCycleState = previousProgressionState.latestUserProgression.methodCycle + 1
                updateMethodProgressStateUseCase(MethodProgressState.ONGOING)
                updateUserProgressionNewCycleContract(nextMethodCycleState)
                if (!usingPreviousRecord) {
                    deleteTrainingMaxesUseCase(nextMethodCycleState)
                    initializeUserTrainingMaxByUpdatedUserProgression(liftCategoryWeightsMap!!)
                }
            }
            is ProgressionState.OnGoing -> {
                throw IllegalProgressionStateSetupException(START_ERROR_MESSAGE)
            }
        }
    }

    private suspend fun initializeUserTrainingMaxByUpdatedUserProgression(liftCategoryWeightsMap: Map<LiftCategory, Int>) {
        val userProgressionAfterUpdatedInThisContext = getUserProgressionAfterUpdated()
        val (updatedMethodCycle, updatedPhase, _) = userProgressionAfterUpdatedInThisContext
        liftCategoryWeightsMap.forEach { (liftCategory, inputWeights) ->
            initTrainingMaxUseCase(liftCategory, inputWeights, updatedMethodCycle, updatedPhase)
        }
    }

    private suspend fun getUserProgressionAfterUpdated(): UserProgression =
        (loadProgressionStateUseCase().first() as ProgressionState.OnGoing).currentUserProgression

}
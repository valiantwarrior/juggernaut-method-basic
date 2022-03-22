package kr.valor.juggernaut.domain.common

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.Companion.KG_FACTOR
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodCycle.Companion.plus
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.usecase.contract.UpdateUserProgressionPhaseContract
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMicroCycleStateUseCase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionsUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.UpdateSessionUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.CalculateNextPhaseTrainingMaxWeightsUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.CalculateTrainingMaxWeightsUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.InitNextPhaseTrainingMaxUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.InitTrainingMaxUseCase
import javax.inject.Inject

class CompleteSessionContract @Inject constructor(
    private val updateSessionUseCase: UpdateSessionUseCase,
    private val findSessionsUseCase: FindSessionsUseCase,
    private val loadProgressionStateUseCase: LoadProgressionStateUseCase,
    private val updateMicroCycleStateUseCase: UpdateMicroCycleStateUseCase,
    private val initNextPhaseTrainingMaxUseCase: InitNextPhaseTrainingMaxUseCase,
    private val updateUserProgressionPhaseContract: UpdateUserProgressionPhaseContract,
    private val completeMethodSetupContract: CompleteMethodSetupContract
) {

    suspend operator fun invoke(session: Session, sessionRecord: SessionRecord) {
        updateSessionUseCase(session, sessionRecord)

        val userProgression =
            (loadProgressionStateUseCase().first() as ProgressionState.OnGoing).currentUserProgression

        if (userProgression.microCycle == MicroCycle.REALIZATION) {
            initNextPhaseTrainingMaxUseCase(session, sessionRecord)
        }

        val isFinished =
            findSessionsUseCase(userProgression).first().all { it.isCompleted }

        with(userProgression) {
            when(isFinished) {
                false -> return
                true -> {
                    if (microCycle == MicroCycle.FINAL) {
                        if (phase == Phase.FINAL) {
                            completeMethodSetupContract()
                        } else {
                            val nextPhase = Phase.values()[phase.ordinal + 1]
                            updateUserProgressionPhaseContract(nextPhase)
                        }
                    } else {
                        val nextMicroCycle = MicroCycle.values()[microCycle.ordinal + 1]
                        updateMicroCycleStateUseCase(nextMicroCycle)
                    }
                }
            }
        }
    }

}
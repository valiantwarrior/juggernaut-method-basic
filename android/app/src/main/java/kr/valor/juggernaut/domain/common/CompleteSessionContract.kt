package kr.valor.juggernaut.domain.common

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.contract.UpdateUserProgressionPhaseContract
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.UpdateMicroCycleStateUseCase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.domain.session.usecase.usecase.CountCompletedSessionsBasedOnUserProgressionUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.UpdateSessionUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.InitNextPhaseTrainingMaxUseCase
import javax.inject.Inject

class CompleteSessionContract @Inject constructor(
    private val updateSessionUseCase: UpdateSessionUseCase,
    private val countCompletedSessionsBasedOnUserProgressionUseCase: CountCompletedSessionsBasedOnUserProgressionUseCase,
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
            countCompletedSessionsBasedOnUserProgressionUseCase(userProgression) == LiftCategory.TOTAL_LIFT_CATEGORY_COUNT

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
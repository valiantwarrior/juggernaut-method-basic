package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.MethodCycle.Companion.plus
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import javax.inject.Inject

class InitNextPhaseTrainingMaxUseCase @Inject constructor(
    private val calculateNextPhaseTrainingMaxWeightsUseCase: CalculateNextPhaseTrainingMaxWeightsUseCase,
    private val initTrainingMaxUseCase: InitTrainingMaxUseCase
) {

    suspend operator fun invoke(session: Session, sessionRecord: SessionRecord) {
        val newTrainingMaxWeights =
            calculateNextPhaseTrainingMaxWeightsUseCase(session, sessionRecord)
        val currentProgression = session.progression
        val (nextMethodCycle, nextPhase) = if (currentProgression.phase == Phase.FINAL) {
            currentProgression.methodCycle + 1 to Phase.INITIAL
        } else {
            currentProgression.methodCycle to Phase.values()[currentProgression.phase.ordinal + 1]
        }

        initTrainingMaxUseCase(session.category, newTrainingMaxWeights, nextMethodCycle, nextPhase)
    }

}
package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.trainingmax.model.CorrespondingBaseRecord
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject

class InitTrainingMaxUseCase @Inject constructor(
    private val repository: TrainingMaxRepository
) {

    suspend operator fun invoke(
        liftCategory: LiftCategory,
        tmWeightsWithCorrespondingBaseRecordPair: Pair<Int, CorrespondingBaseRecord>,
        methodCycleWithPhasePair: Pair<MethodCycle, Phase>
    ) {
        val newTrainingMax =
            repository.createTrainingMax(
                liftCategory,
                tmWeightsWithCorrespondingBaseRecordPair,
                methodCycleWithPhasePair
            )

        repository.insertTrainingMax(newTrainingMax)
    }

}
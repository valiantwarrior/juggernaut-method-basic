package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.LiftCategory.Companion.KG_FACTOR
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.common.di.KgWeightUnit
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import javax.inject.Inject

class CalculateNextPhaseTrainingMaxWeightsUseCase @Inject constructor(
    @KgWeightUnit private val weightUnitTransformer: WeightUnitTransformer
) {

    operator fun invoke(session: Session, sessionRecord: SessionRecord): Int {
        val prRepetitions = sessionRecord.repetitionsRecord!!
        val baseRepetitions = session.sessionProgression.baseAmrapRepetitions
        val baseTmWeights = session.tmWeights
        val baseIncrement = session.category.baseIncrement * KG_FACTOR

        val newTrainingMaxWeightsRaw =
            ((prRepetitions - baseRepetitions) * baseIncrement) + baseTmWeights

        return weightUnitTransformer.transform(newTrainingMaxWeightsRaw)
    }

}
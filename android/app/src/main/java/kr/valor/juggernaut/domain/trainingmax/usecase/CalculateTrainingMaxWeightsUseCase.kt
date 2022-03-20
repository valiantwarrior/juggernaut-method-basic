package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.common.di.KgWeightUnit
import javax.inject.Inject

class CalculateTrainingMaxWeightsUseCase @Inject constructor(
    @KgWeightUnit private val weightUnitTransformer: WeightUnitTransformer
) {

    operator fun invoke(inputWeights: Double) =
        weightUnitTransformer.transform(inputWeights * 0.9)

}
package kr.valor.juggernaut.domain.trainingmax.usecase

import javax.inject.Inject
import kotlin.math.round

class CalculateOneRepMaxUseCase @Inject constructor() {

    operator fun invoke(inputWeights: Double, inputRepetitions: Int): Double {
        return if (inputRepetitions == 1) inputWeights else {
            round(inputWeights * (1 + inputRepetitions * 0.0333))
        }
    }

}
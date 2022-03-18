package kr.valor.juggernaut.data.common.converter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KgWeightUnitTransformer @Inject constructor(): WeightUnitTransformer {

    override fun transform(input: Double): Int {
        val roundedDownTowardZero = input.toInt()
        val quotient = roundedDownTowardZero / CEILING_UP_BASE

        return if (quotient % 2 == 0)
            roundedDownTowardZero else (quotient + 1) * CEILING_UP_BASE
    }

    companion object {
        private const val CEILING_UP_BASE = 2
    }

}
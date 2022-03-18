package kr.valor.juggernaut.data.common.converter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KgWeightUnitTransformer @Inject constructor(): WeightUnitTransformer {

    override fun transform(input: Double): Int {
        val roundedDownTowardZero = input.toInt()

        return when(roundedDownTowardZero % 2) {
            0 -> {
                if (input % 2.0 == 0.0) {
                    input.toInt()
                } else {
                    roundedDownTowardZero + CEILING_UP_BASE
                }
            }
            else -> roundedDownTowardZero + 1
        }
    }

    companion object {
        private const val CEILING_UP_BASE = 2
    }

}
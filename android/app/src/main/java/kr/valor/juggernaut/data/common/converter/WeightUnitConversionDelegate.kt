package kr.valor.juggernaut.data.common.converter

interface WeightUnitConversionDelegate {
    fun convert(input: Double): Int
}

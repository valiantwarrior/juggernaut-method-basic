package kr.valor.juggernaut.data.session.mapper.delegate.property

object DefaultPropertyMediateDelegate: RoutinePropertyMediateDelegate {

    private const val CEILING_UP_BASE = 2

    override fun mediate(input: Double): Int {
        val roundedDownTowardZero = input.toInt()
        val quotient = roundedDownTowardZero / CEILING_UP_BASE

        return if (quotient % 2 == 0)
            roundedDownTowardZero else (quotient + 1) * CEILING_UP_BASE
    }

}
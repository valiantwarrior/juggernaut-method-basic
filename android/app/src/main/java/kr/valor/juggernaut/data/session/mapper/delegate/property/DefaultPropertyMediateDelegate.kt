package kr.valor.juggernaut.data.session.mapper.delegate.property

import kotlin.math.ceil

object DefaultPropertyMediateDelegate: RoutinesPropertyMediateDelegate {
    override fun mediate(input: Double): Double =
        ceil(input)
}
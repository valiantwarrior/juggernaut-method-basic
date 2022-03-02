package kr.valor.juggernaut.data.session.mapper.delegate.property

import kotlin.math.round

object DefaultPropertyMediateDelegate: RoutinesPropertyMediateDelegate {
    override fun mediate(input: Double): Double =
        round(input)
}
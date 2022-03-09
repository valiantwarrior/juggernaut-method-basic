package kr.valor.juggernaut.data.common

interface EntityMapper<E, D> {
    fun map(entity: E): D
}
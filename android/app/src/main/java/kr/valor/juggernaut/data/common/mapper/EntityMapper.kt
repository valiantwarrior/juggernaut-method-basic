package kr.valor.juggernaut.data.common.mapper

interface EntityMapper<E, D> {
    fun map(entity: E): D
}
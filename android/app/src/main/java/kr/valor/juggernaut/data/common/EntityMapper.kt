package kr.valor.juggernaut.data.common

interface EntityMapper<E, D> {
    fun E.toDomainModel(): D
}
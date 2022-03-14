package kr.valor.juggernaut.data.common.mapper

interface EntityMapper<E, D> {

    fun mapEntity(entity: E): D

}
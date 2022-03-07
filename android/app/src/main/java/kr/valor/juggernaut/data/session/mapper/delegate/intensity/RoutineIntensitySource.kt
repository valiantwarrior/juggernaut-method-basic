package kr.valor.juggernaut.data.session.mapper.delegate.intensity

import kr.valor.juggernaut.domain.session.model.RoutineIntensity

interface RoutineIntensitySource<P, K> {
    fun provideRoutineIntensityMap(key: P): Map<K, List<RoutineIntensity>>
}
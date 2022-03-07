package kr.valor.juggernaut.data.session.mapper.delegate

import kr.valor.juggernaut.domain.session.model.Routine

interface RoutineProviderDelegate<P> {
    fun provideSessionRoutine(progression: P, tmWeights: Double): List<Routine>
}
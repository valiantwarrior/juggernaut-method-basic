package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.domain.session.model.Routine

interface RoutineProviderDelegate<P> {
    fun provideSessionRoutine(progression: P, tmWeights: Int, actualRepetitions: Int?): List<Routine>
}
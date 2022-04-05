package kr.valor.juggernaut.data.session.mapper.delegate.routine


interface RoutineProviderDelegate<P, out T> {
    fun provideSessionRoutine(progression: P, tmWeights: Int, actualRepetitions: Int?): T
}
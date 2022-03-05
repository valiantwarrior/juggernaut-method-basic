package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.session.model.SessionRoutine

interface RoutinesProviderDelegate {
    fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine
}
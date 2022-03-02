package kr.valor.juggernaut.data.session.mapper.delegate.provider

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine as SessionRoutine

interface RoutinesProviderDelegate {
    fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine
}
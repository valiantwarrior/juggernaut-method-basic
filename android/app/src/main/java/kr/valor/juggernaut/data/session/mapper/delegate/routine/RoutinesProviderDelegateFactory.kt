package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.BasicMethodRoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.AmrapSession.Progressions

interface RoutinesProviderDelegateFactory {
    fun provide(progressions: Progressions, ceilUserPreferences: Double?): BasicMethodRoutinesProviderDelegate
}
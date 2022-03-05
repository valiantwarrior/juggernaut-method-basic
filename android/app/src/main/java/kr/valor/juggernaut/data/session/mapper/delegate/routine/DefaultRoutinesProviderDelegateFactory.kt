package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.BasicMethodRoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.AmrapSession.Progressions

class DefaultRoutinesProviderDelegateFactory: RoutinesProviderDelegateFactory {
    override fun provide(progressions: Progressions, ceilUserPreferences: Double?): BasicMethodRoutinesProviderDelegate {
        TODO()
//        return when(progressions.microCycle) {
//            ACCUMULATION -> AccumulationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//            INTENSIFICATION -> IntensificationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//            REALIZATION -> RealizationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//            DELOAD -> DeloadRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//        }
    }
}
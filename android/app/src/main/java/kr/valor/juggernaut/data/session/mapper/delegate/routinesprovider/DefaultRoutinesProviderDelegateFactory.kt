package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider

import kr.valor.juggernaut.domain.session.model.Session.Progressions

class DefaultRoutinesProviderDelegateFactory: RoutinesProviderDelegateFactory {
    override fun provide(progressions: Progressions, ceilUserPreferences: Double?): RoutinesProviderDelegate {
        TODO()
//        return when(progressions.microCycle) {
//            ACCUMULATION -> AccumulationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//            INTENSIFICATION -> IntensificationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//            REALIZATION -> RealizationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//            DELOAD -> DeloadRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
//        }
    }
}
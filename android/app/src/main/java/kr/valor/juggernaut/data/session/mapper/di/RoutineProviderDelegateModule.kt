package kr.valor.juggernaut.data.session.mapper.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.session.mapper.delegate.routine.AmrapRoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.SessionProgression

@InstallIn(SingletonComponent::class)
@Module
abstract class RoutineProviderDelegateModule {

    @Binds
    abstract fun bindMethodRoutineProviderDelegate(
        impl: BasicMethodRoutineProviderDelegate
    ): RoutineProviderDelegate<SessionProgression, List<Routine>>

    @Binds
    abstract fun bindAmrapRoutineProviderDelegate(
        impl: AmrapRoutineProviderDelegate
    ): RoutineProviderDelegate<SessionProgression, Routine>

}
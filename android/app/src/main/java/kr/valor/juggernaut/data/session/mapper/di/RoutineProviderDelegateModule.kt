package kr.valor.juggernaut.data.session.mapper.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Progression

@InstallIn(SingletonComponent::class)
@Module
abstract class RoutineProviderDelegateModule {

    @Binds
    abstract fun bindRoutineProviderDelegate(impl: BasicMethodRoutineProviderDelegate): RoutineProviderDelegate<Progression>

}
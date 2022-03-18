package kr.valor.juggernaut.data.session.mapper.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InMemorySource

@InstallIn(SingletonComponent::class)
@Module
abstract class RoutineIntensitySourceModule {

    @InMemorySource
    @Singleton
    @Binds
    abstract fun bindRoutineIntensitySource(impl: InMemoryRoutineIntensitySource): RoutineIntensitySource<MicroCycle, Phase>

}
package kr.valor.juggernaut.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.DefaultProgressionStateRepository
import kr.valor.juggernaut.data.DefaultSessionRepository
import kr.valor.juggernaut.data.DefaultTrainingMaxRepository
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FakeRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @DefaultRepository
    @Singleton
    @Binds
    abstract fun bindSessionRepository(impl: DefaultSessionRepository): SessionRepository

    @DefaultRepository
    @Singleton
    @Binds
    abstract fun bindTrainingMaxRepository(impl: DefaultTrainingMaxRepository): TrainingMaxRepository

    @DefaultRepository
    @Singleton
    @Binds
    abstract fun bindProgressionStateRepository(impl: DefaultProgressionStateRepository): ProgressionStateRepository

}
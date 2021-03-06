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

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSessionRepository(impl: DefaultSessionRepository): SessionRepository

    @Singleton
    @Binds
    abstract fun bindTrainingMaxRepository(impl: DefaultTrainingMaxRepository): TrainingMaxRepository

    @Singleton
    @Binds
    abstract fun bindProgressionStateRepository(impl: DefaultProgressionStateRepository): ProgressionStateRepository

}
package kr.valor.juggernaut.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.DefaultSessionRepository
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class SessionRepositoryModule {

    @DefaultRepository
    @Singleton
    @Binds
    abstract fun bindSessionRepository(impl: DefaultSessionRepository): SessionRepository

}
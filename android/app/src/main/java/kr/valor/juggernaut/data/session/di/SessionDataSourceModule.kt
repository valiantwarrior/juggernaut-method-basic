package kr.valor.juggernaut.data.session.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.session.SessionDao
import kr.valor.juggernaut.data.session.source.SessionDataSource

@InstallIn(SingletonComponent::class)
@Module
abstract class SessionDataSourceModule {

    @Binds
    abstract fun bindSessionDataSource(sessionDao: SessionDao): SessionDataSource

}
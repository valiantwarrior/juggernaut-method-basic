package kr.valor.juggernaut.data.session.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.session.mapper.DefaultSessionEntityMapper
import kr.valor.juggernaut.data.session.mapper.SessionMapper

@InstallIn(SingletonComponent::class)
@Module
abstract class SessionMapperModule {

    @Binds
    abstract fun bindSessionMapper(impl: DefaultSessionEntityMapper): SessionMapper

}
package kr.valor.juggernaut.data.progression.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.progression.PreferencesProgressionStateSource
import kr.valor.juggernaut.data.progression.source.ProgressionStateDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStoreSource

@InstallIn(SingletonComponent::class)
@Module
abstract class ProgressionStateDataSourceModule {

    @DataStoreSource
    @Singleton
    @Binds
    abstract fun bindProgressionStateDataSource(impl: PreferencesProgressionStateSource): ProgressionStateDataSource

}

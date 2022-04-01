package kr.valor.juggernaut.data.settings.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.settings.PreferencesSettingsSource
import kr.valor.juggernaut.data.settings.source.SettingsDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class SettingsDataSourceModule {

    @Singleton
    @Binds
    abstract fun provideSettingsDataSource(impl: PreferencesSettingsSource): SettingsDataSource

}
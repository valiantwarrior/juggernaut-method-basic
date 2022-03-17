package kr.valor.juggernaut.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    private const val PROGRESSION_STATE_PREFERENCES = "progression_state_preferences"

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { applicationContext.preferencesDataStoreFile(
                PROGRESSION_STATE_PREFERENCES)
            }
        )

}
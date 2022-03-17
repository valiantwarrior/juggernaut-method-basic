package kr.valor.juggernaut.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)
@Module
object TestDataStoreModule {

    private const val TEST_PROGRESSION_STATE_PREFERENCES = "test_progression_state_preferences"

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = TestScope(UnconfinedTestDispatcher() + Job()),
            produceFile = {
                applicationContext.preferencesDataStoreFile(TEST_PROGRESSION_STATE_PREFERENCES)
            }
        )
    }

}
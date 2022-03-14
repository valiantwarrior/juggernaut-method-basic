package kr.valor.juggernaut.data.user.progression

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.user.progression.source.UserProgressionDataSource
import kr.valor.juggernaut.domain.user.model.UserProgression
import java.io.IOException

class PreferencesUserProgressionSource(
    private val dataStore: DataStore<Preferences>
): UserProgressionDataSource {

    private object PreferencesKeys {
        val METHOD_CYCLE = intPreferencesKey("method_cycle")
        val PHASE = stringPreferencesKey("phase")
        val MICRO_CYCLE = stringPreferencesKey("micro_cycle")
    }
    
    override fun getUserProgressionData(): Flow<UserProgression> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapUserProgression(preferences)
        }

    override suspend fun editUserProgression(progressionElement: ProgressionElement) {
        when(progressionElement) {
            is MethodCycle -> editPreference(progressionElement.value, PreferencesKeys.METHOD_CYCLE)
            is Phase -> editPreference(progressionElement.name, PreferencesKeys.PHASE)
            is MicroCycle -> editPreference(progressionElement.name, PreferencesKeys.MICRO_CYCLE)
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    private suspend inline fun <T> editPreference(value: T, key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun mapUserProgression(preferences: Preferences): UserProgression {
        val methodCycle = MethodCycle(
            preferences[PreferencesKeys.METHOD_CYCLE] ?: 1
        )

        val phase = Phase.valueOf(
            preferences[PreferencesKeys.PHASE] ?: Phase.REP10.name
        )
        val cycle = MicroCycle.valueOf(
            preferences[PreferencesKeys.MICRO_CYCLE] ?: MicroCycle.ACCUMULATION.name
        )

        return UserProgression(methodCycle, phase, cycle)
    }
}
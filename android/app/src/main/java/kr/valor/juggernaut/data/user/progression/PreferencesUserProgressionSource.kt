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
        val CYCLE = stringPreferencesKey("micro_cycle")
    }

    private val userProgressionFlow: Flow<UserProgression> = dataStore.data
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

    override fun getUserProgressionData(): Flow<UserProgression> =
        userProgressionFlow

    override suspend fun editUserProgression(progressionElement: ProgressionElement) {
        when(progressionElement) {
            is MethodCycle -> editMethodCyclePreference(progressionElement)
            is Phase -> editPhasePreference(progressionElement)
            is MicroCycle -> editMicroCyclePreference(progressionElement)
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    private suspend fun editMethodCyclePreference(methodCycle: MethodCycle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.METHOD_CYCLE] = methodCycle.value
        }
    }

    private suspend fun editMicroCyclePreference(microCycle: MicroCycle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CYCLE] = microCycle.name
        }
    }

    private suspend fun editPhasePreference(phase: Phase) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PHASE] = phase.name
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
            preferences[PreferencesKeys.CYCLE] ?: MicroCycle.ACCUMULATION.name
        )


        return UserProgression(methodCycle, phase, cycle)
    }
}
package kr.valor.juggernaut.data.user.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.model.UserProgression
import java.io.IOException

class PreferencesUserProgressionSource(
    private val dataStore: DataStore<Preferences>
): UserProgressionDataSource {

    private object PreferencesKeys {
        val PHASE = stringPreferencesKey("phase")
        val CYCLE = stringPreferencesKey("micro_cycle")
        val CATEGORY = stringPreferencesKey("lift_category")
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

    override suspend fun editUserProgressionMicroCycle(microCycle: MicroCycle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CYCLE] = microCycle.name
        }
    }

    override suspend fun editUserProgressionPhase(phase: Phase) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PHASE] = phase.name
        }
    }

    override suspend fun editUserProgressionCategory(liftCategory: LiftCategory) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CATEGORY] = liftCategory.name
        }
    }

    private fun mapUserProgression(preferences: Preferences): UserProgression {
        val phase = Phase.valueOf(
            preferences[PreferencesKeys.PHASE] ?: Phase.REP10.name
        )
        val cycle = MicroCycle.valueOf(
            preferences[PreferencesKeys.CYCLE] ?: MicroCycle.ACCUMULATION.name
        )
        val category = LiftCategory.valueOf(
            preferences[PreferencesKeys.CATEGORY] ?: LiftCategory.BENCH_PRESS.name
        )

        return UserProgression(phase, cycle, category)
    }
}
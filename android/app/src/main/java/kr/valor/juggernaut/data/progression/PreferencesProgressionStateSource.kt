package kr.valor.juggernaut.data.progression

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesProgressionStateSource @Inject constructor(
     private val dataStore: DataStore<Preferences>
): ProgressionStateDataSource {

    private object PreferencesKeys {
        val METHOD_CYCLE = intPreferencesKey("method_cycle")
        val PHASE = stringPreferencesKey("phase")
        val MICRO_CYCLE = stringPreferencesKey("micro_cycle")
        val METHOD_PROGRESS_STATE = stringPreferencesKey("method_progress_state")
    }
    
    override fun getProgressionStateData(): Flow<ProgressionState> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            it.toDomainModel()
        }

    override suspend fun editMethodCycleState(methodCycle: MethodCycle) {
        editPreference(methodCycle.value, PreferencesKeys.METHOD_CYCLE)
    }

    override suspend fun editPhaseState(phase: Phase) {
        editPreference(phase.name, PreferencesKeys.PHASE)
    }

    override suspend fun editMicroCycleState(microCycle: MicroCycle) {
        editPreference(microCycle.name, PreferencesKeys.MICRO_CYCLE)
    }

    override suspend fun editMethodProgressState(methodProgressState: MethodProgressState) {
        editPreference(methodProgressState.name, PreferencesKeys.METHOD_PROGRESS_STATE)
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.METHOD_CYCLE)
            preferences.remove(PreferencesKeys.PHASE)
            preferences.remove(PreferencesKeys.MICRO_CYCLE)
        }
        editPreference(MethodProgressState.NONE.name, PreferencesKeys.METHOD_PROGRESS_STATE)
    }

    private suspend inline fun <T> editPreference(value: T, key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun Preferences.toDomainModel(): ProgressionState {
        val methodProgressionState = MethodProgressState.valueOf(
            value = retrievePreferencesValueByKey(PreferencesKeys.METHOD_PROGRESS_STATE, MethodProgressState.NONE.name, true)
        )

        return when(methodProgressionState) {
            MethodProgressState.NONE -> ProgressionState.None
            MethodProgressState.ONGOING -> ProgressionState.OnGoing(
                currentUserProgression = retrieveUserProgressionFromPreferences(true)
            )
            MethodProgressState.DONE -> ProgressionState.Done(
                latestUserProgression = retrieveUserProgressionFromPreferences(false)
            )
        }
    }

    private fun Preferences.retrieveUserProgressionFromPreferences(mayNullable: Boolean): UserProgression {
        val methodCycle = MethodCycle(
            value = retrievePreferencesValueByKey(PreferencesKeys.METHOD_CYCLE, MethodCycle.INITIAL_VALUE, mayNullable)
        )
        val phase = Phase.valueOf(
            value = retrievePreferencesValueByKey(PreferencesKeys.PHASE, Phase.INITIAL.name, mayNullable)
        )
        val microCycle = MicroCycle.valueOf(
            value = retrievePreferencesValueByKey(PreferencesKeys.MICRO_CYCLE, MicroCycle.INITIAL.name, mayNullable)
        )

        return UserProgression(methodCycle, phase, microCycle)
    }

    private fun <T> Preferences.retrievePreferencesValueByKey(key: Preferences.Key<T>, defaultValue: T, nullablePreferences: Boolean): T =
        if (nullablePreferences) {
            this[key] ?: defaultValue
        } else {
            this[key]!!
        }

}
package kr.valor.juggernaut.data.settings

import android.os.Build
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.data.settings.PreferencesSettingsSource.PreferencesKeys.SELECT_THEME
import kr.valor.juggernaut.data.settings.source.SettingsDataSource
import kr.valor.juggernaut.domain.settings.model.Theme
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesSettingsSource  @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsDataSource {

    private object PreferencesKeys {
        val SELECT_THEME = stringPreferencesKey("theme")
    }

    override suspend fun editTheme(themeName: String) {
        dataStore.edit { dataStore ->
            dataStore[SELECT_THEME] = themeName
        }
    }

    override val currentTheme: Flow<Theme> =
        dataStore.data.map { dataStore ->
            Theme.valueOf(
                dataStore[SELECT_THEME] ?: when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> Theme.SYSTEM.name
                    else -> Theme.BATTERY_SAVER.name
                }
            )
        }

}
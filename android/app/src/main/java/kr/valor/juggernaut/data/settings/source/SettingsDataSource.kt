package kr.valor.juggernaut.data.settings.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.settings.model.Theme

interface SettingsDataSource {
    suspend fun editTheme(themeName: String)
    val currentTheme: Flow<Theme>
}
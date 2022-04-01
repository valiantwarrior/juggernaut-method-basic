package kr.valor.juggernaut.domain.settings.usecase

import kr.valor.juggernaut.data.settings.PreferencesSettingsSource
import javax.inject.Inject

class UpdateThemeUseCase @Inject constructor(
    private val preferencesSettingsSource: PreferencesSettingsSource
) {

    suspend operator fun invoke(themeName: String) {
        preferencesSettingsSource.editTheme(themeName)
    }

}
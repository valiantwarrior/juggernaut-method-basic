package kr.valor.juggernaut.domain.settings.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.data.settings.PreferencesSettingsSource
import kr.valor.juggernaut.domain.settings.model.Theme
import javax.inject.Inject

class LoadThemeUseCase @Inject constructor(
    private val preferencesSettingsSource: PreferencesSettingsSource
) {

    operator fun invoke(): Flow<Theme> =
        preferencesSettingsSource.currentTheme

}
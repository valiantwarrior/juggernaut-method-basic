package kr.valor.juggernaut.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.common.HaltMethodStateContract
import kr.valor.juggernaut.domain.settings.model.Theme
import kr.valor.juggernaut.domain.settings.usecase.GetAvailableThemesUseCase
import kr.valor.juggernaut.domain.settings.usecase.LoadThemeUseCase
import kr.valor.juggernaut.domain.settings.usecase.UpdateThemeUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val haltMethodStateContract: HaltMethodStateContract,
    private val updateThemeUseCase: UpdateThemeUseCase,
    loadThemeUseCase: LoadThemeUseCase,
    getAvailableThemesUseCase: GetAvailableThemesUseCase
) : ViewModel() {

    private val _settingsUiEvent = MutableSharedFlow<SettingsUiAction>()
    val uiEventFlow: Flow<SettingsUiAction>
        get() = _settingsUiEvent

    val availableThemes: StateFlow<List<Theme>> = flow {
        emit(getAvailableThemesUseCase())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), listOf())

    val selectedTheme: StateFlow<Theme> = flow {
        emit(loadThemeUseCase().first())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), Theme.SYSTEM)

    fun onHaltMethodClicked() {
        viewModelScope.launch {
            haltMethodStateContract()
            _settingsUiEvent.emit(SettingsUiAction.HaltMethod)
        }
    }

    fun onClickChooseTheme() {
        viewModelScope.launch {
            _settingsUiEvent.emit(SettingsUiAction.SelectTheme)
        }
    }

    fun onThemeSelected(theme: Theme) {
        viewModelScope.launch {
            updateThemeUseCase(theme.name)
        }
    }

}

sealed class SettingsUiAction {
    object HaltMethod: SettingsUiAction()
    object SelectTheme: SettingsUiAction()
}
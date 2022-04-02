package kr.valor.juggernaut.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.settings.model.Theme
import kr.valor.juggernaut.domain.settings.usecase.LoadThemeUseCase
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase,
    loadThemeUseCase: LoadThemeUseCase
): ViewModel() {

    val launchAction: Flow<LaunchAction>

    init {
        val launchActionState = Channel<LaunchAction>()

        viewModelScope.launch {
            val currentTheme = loadThemeUseCase().first()
            val launchAction = when(loadProgressionStateUseCase().first()) {
                is ProgressionState.None -> LaunchAction.Onboarding(currentTheme)
                else -> LaunchAction.Main(currentTheme)
            }

            launchActionState.send(launchAction)
        }
        launchAction = launchActionState.receiveAsFlow()
    }

}

sealed class LaunchAction {
    data class Main(val currentTheme: Theme): LaunchAction()
    data class Onboarding(val currentTheme: Theme): LaunchAction()
}
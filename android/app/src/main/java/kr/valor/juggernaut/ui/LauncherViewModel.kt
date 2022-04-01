package kr.valor.juggernaut.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val launchAction = loadProgressionStateUseCase().map { progressionState ->
        Log.d("TAG", "MAP")
        val theme = loadThemeUseCase().first()
        when(progressionState) {
            is ProgressionState.None -> LaunchAction.Onboarding(theme)
            else -> LaunchAction.Main(theme)
        }
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, LaunchAction.Loading)

}

sealed class LaunchAction {
    object Loading: LaunchAction()
    data class Main(val theme: Theme): LaunchAction()
    data class Onboarding(val theme: Theme): LaunchAction()
}
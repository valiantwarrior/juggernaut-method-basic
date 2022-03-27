package kr.valor.juggernaut.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase
): ViewModel() {

    val navigateEvent = loadProgressionStateUseCase().map { progressionState ->
        when(progressionState) {
            is ProgressionState.None -> LaunchNavigationAction.NavigateMain
            else -> LaunchNavigationAction.NavigateOnboarding
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, LaunchNavigationAction.Loading)
}

sealed class LaunchNavigationAction {
    object Loading: LaunchNavigationAction()
    object NavigateMain: LaunchNavigationAction()
    object NavigateOnboarding: LaunchNavigationAction()
}
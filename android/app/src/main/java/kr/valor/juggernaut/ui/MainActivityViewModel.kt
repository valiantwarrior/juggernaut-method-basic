package kr.valor.juggernaut.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase
): ViewModel() {

    val navigationEventLiveData: LiveData<NavigationEvent> = liveData {
        when(loadProgressionStateUseCase().first()) {
            is ProgressionState.None -> emit(NavigationEvent.NavigateToEmpty)
            else -> emit(NavigationEvent.NavigateToHome)
        }
    }

}

sealed class NavigationEvent {
    object NavigateToHome: NavigationEvent()
    object NavigateToEmpty: NavigationEvent()
}
package kr.valor.juggernaut.ui.overall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionsUseCase
import javax.inject.Inject

@HiltViewModel
class OverallViewModel @Inject constructor(
    loadSessionsUseCase: LoadSessionsUseCase
) : ViewModel() {

    private val _eventChannel: Channel<OverallUiEvent> = Channel()
    val uiEventFlow: Flow<OverallUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState: StateFlow<OverallUiState> = loadSessionsUseCase().map { sessions ->
        val completedSessions = sessions.filter { session ->
            session.isCompleted
        }
        OverallUiState.Result(completedSessions)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), OverallUiState.Loading)

    fun onClickItem(sessionId: Long) {
        viewModelScope.launch {
            _eventChannel.send(OverallUiEvent.NavigateAccomplishment(sessionId))
        }
    }
}

sealed class OverallUiEvent {
    data class NavigateAccomplishment(val sessionId: Long): OverallUiEvent()
}

sealed class OverallUiState {
    object Loading: OverallUiState()
    data class Result(val sessions: List<Session>): OverallUiState()
}
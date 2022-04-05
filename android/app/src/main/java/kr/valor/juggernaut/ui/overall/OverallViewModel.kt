package kr.valor.juggernaut.ui.overall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionSummariesUseCase
import kr.valor.juggernaut.ui.common.WhileViewSubscribed
import javax.inject.Inject

@HiltViewModel
class OverallViewModel @Inject constructor(
    loadSessionSummariesUseCase: LoadSessionSummariesUseCase
) : ViewModel() {

    private val _eventChannel: Channel<OverallUiEvent> = Channel()
    val uiEventFlow: Flow<OverallUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState: StateFlow<OverallUiState> = loadSessionSummariesUseCase().map { sessionSummaries ->
        val completedSessionSummaries = sessionSummaries.filter { sessionSummary ->
            sessionSummary.isCompletedSession
        }
        OverallUiState.Result(completedSessionSummaries)
    }.stateIn(viewModelScope, WhileViewSubscribed, OverallUiState.Loading)

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
    data class Result(val sessionSummaries: List<SessionSummary>): OverallUiState()
    object Loading: OverallUiState()
}
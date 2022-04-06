package kr.valor.juggernaut.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.common.WhileViewSubscribed
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryViewModelDelegate
import javax.inject.Inject

sealed class DetailUiEvent {
    data class NavigateAccomplishment(val sessionId: Long): DetailUiEvent()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    sessionSummaryViewModelDelegate: SessionSummaryViewModelDelegate,
) : ViewModel(), SessionSummaryViewModelDelegate by sessionSummaryViewModelDelegate {

    private val _eventChannel: Channel<DetailUiEvent> = Channel()
    val uiEventFlow: Flow<DetailUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState =
        userProgressionWithSessionSummaries.map { (userProgression, sessionSummaries) ->
            val sortedSessionSummaries = sessionSummaries
                .filter { it.isCompletedSession }.sortedBy { it.category.ordinal }

            return@map DetailUiState.Result(userProgression, sortedSessionSummaries)
        }.stateIn(viewModelScope, WhileViewSubscribed, DetailUiState.Loading)

    fun onClickItem(sessionId: Long) {
        viewModelScope.launch {
            _eventChannel.send(DetailUiEvent.NavigateAccomplishment(sessionId))
        }
    }

}

sealed class DetailUiState {
    data class Result(
        val userProgression: UserProgression,
        val sessionSummaries: List<SessionSummary>
    ): DetailUiState()

    object Loading: DetailUiState()
}
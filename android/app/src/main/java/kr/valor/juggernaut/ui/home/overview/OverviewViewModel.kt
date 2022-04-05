package kr.valor.juggernaut.ui.home.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.domain.session.usecase.contract.SynchronizeSessionsContract
import kr.valor.juggernaut.ui.common.WhileViewSubscribed
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryViewModelDelegate
import javax.inject.Inject

sealed class OverviewUiEvent {
    data class NavigatePreview(val sessionId: Long): OverviewUiEvent()
    data class NavigateAccomplishment(val sessionId: Long): OverviewUiEvent()
}

@ExperimentalCoroutinesApi
@HiltViewModel
class OverviewViewModel @Inject constructor(
    sessionSummaryViewModelDelegate: SessionSummaryViewModelDelegate,
    private val synchronizeSessionsContract: SynchronizeSessionsContract
) : ViewModel(), SessionSummaryViewModelDelegate by sessionSummaryViewModelDelegate {

    private val _eventChannel: Channel<OverviewUiEvent> = Channel()
    val uiEventFlow: Flow<OverviewUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState =
        sessionSummaryState.map { (userProgression, sessionSummaries) ->
            val sortedSessionSummaries = sessionSummaries.sortedWith(
                compareBy<SessionSummary> { it.isCompletedSession }.thenBy { it.category.ordinal }
            )

            return@map OverviewUiState.Result(
                userProgression = userProgression,
                sessionSummaries = sortedSessionSummaries
            )
        }.stateIn(viewModelScope, WhileViewSubscribed, OverviewUiState.Loading)

    init {
        viewModelScope.launch {
            synchronizeSessionsContract()
        }
    }

    fun onClickSessionItem(sessionId: Long) {
        viewModelScope.launch {
            val isCompletedSession = when(val uiState = uiState.value) {
                is OverviewUiState.Result -> {
                    val clickedSession =
                        uiState.sessionSummaries.find { it.sessionId == sessionId }!!

                    clickedSession.completedLocalDateTime != null
                }
                else -> return@launch
            }

            if (isCompletedSession) {
                _eventChannel.send(OverviewUiEvent.NavigateAccomplishment(sessionId))
            } else {
                _eventChannel.send(OverviewUiEvent.NavigatePreview(sessionId))
            }
        }
    }

}

sealed class OverviewUiState {
    data class Result(
        val userProgression: UserProgression,
        val sessionSummaries: List<SessionSummary>
    ): OverviewUiState()

    object Loading: OverviewUiState()
}
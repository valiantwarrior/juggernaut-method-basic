package kr.valor.juggernaut.ui.session.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.CountCompletedSessionsBasedOnUserProgressionUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionByIdOneShotUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import kr.valor.juggernaut.ui.common.WhileViewSubscribed
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionByIdOneShotUseCase: FindSessionByIdOneShotUseCase,
    countCompletedSessionsBasedOnUserProgressionUseCase: CountCompletedSessionsBasedOnUserProgressionUseCase
): ViewModel() {

    private val _eventChannel: Channel<PreviewUiEvent> = Channel()
    val uiEventFlow: Flow<PreviewUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState = flow {
        val session = findSessionByIdOneShotUseCase(
            sessionId = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!
        )
        emit(session)
    }.map { session ->
        val userProgression = with(session.sessionProgression) {
            UserProgression(methodCycle, phase, microCycle)
        }

        return@map PreviewUiState.Result(
            session = session,
            totalCompletedSessionsCount = countCompletedSessionsBasedOnUserProgressionUseCase(userProgression)
        )
    }.stateIn(viewModelScope, WhileViewSubscribed, PreviewUiState.Loading)

    fun onClickStart() {
        viewModelScope.launch {
            val session = (uiState.value as PreviewUiState.Result).session
            val sessionId = session.sessionId
            val baseAmrapRepetitions = session.sessionProgression.baseAmrapRepetitions
            val sessionOrdinal =  (uiState.value as PreviewUiState.Result).totalCompletedSessionsCount + 1
            _eventChannel.send(PreviewUiEvent.StartSession(sessionId, baseAmrapRepetitions, sessionOrdinal))
        }
    }

}

sealed class PreviewUiState {
    object Loading: PreviewUiState()
    data class Result(val session: Session, val totalCompletedSessionsCount: Int): PreviewUiState()
}

sealed class PreviewUiEvent {
    data class StartSession(
        val sessionId: Long,
        val baseAmrapRepetitions: Int,
        val sessionOrdinal: Int
    ): PreviewUiEvent()
}
package kr.valor.juggernaut.ui.session.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionOneShotUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionUseCase: FindSessionUseCase
): ViewModel() {

    private val _eventChannel: Channel<PreviewUiEvent> = Channel()
    val uiEventFlow: Flow<PreviewUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState = findSessionUseCase(sessionId = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!)
        .map { session -> PreviewUiState.Result(session) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), PreviewUiState.Loading)

    fun onClickStart() {
        viewModelScope.launch {
            val session = (uiState.value as PreviewUiState.Result).session
            val sessionId = session.sessionId
            val baseAmrapRepetitions = session.progression.baseAmrapRepetitions

            _eventChannel.send(PreviewUiEvent.StartSession(sessionId, baseAmrapRepetitions))
        }
    }

}

sealed class PreviewUiState {
    object Loading: PreviewUiState()
    data class Result(val session: Session): PreviewUiState()
}

sealed class PreviewUiEvent {
    data class StartSession(
        val sessionId: Long,
        val baseAmrapRepetitions: Int
    ): PreviewUiEvent()
    object Back: PreviewUiEvent()
}
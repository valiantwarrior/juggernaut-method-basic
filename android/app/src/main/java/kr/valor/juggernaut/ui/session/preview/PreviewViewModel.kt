package kr.valor.juggernaut.ui.session.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionOneShotUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import javax.inject.Inject

sealed class PreviewUiEvent {
    data class StartSession(val sessionId: Long): PreviewUiEvent()
    object Back: PreviewUiEvent()
}

@HiltViewModel
class PreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionOneShotUseCase: FindSessionOneShotUseCase
): ViewModel() {

    private val _eventChannel: Channel<PreviewUiEvent> = Channel()
    val uiEventFlow: Flow<PreviewUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val session = liveData {
        val sessionId: Long = savedStateHandle.get(NAV_ARGS_SESSION_ID_KEY)!!
        emit(findSessionOneShotUseCase(sessionId))
    }

    fun onClickStart(sessionId: Long) {
        viewModelScope.launch {
            _eventChannel.send(PreviewUiEvent.StartSession(sessionId))
        }
    }

}
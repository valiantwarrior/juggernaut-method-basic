package kr.valor.juggernaut.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionsUseCase
import javax.inject.Inject

sealed class DetailUiEvent {
    data class NavigateAccomplishment(val sessionId: Long): DetailUiEvent()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    loadSessionsUseCase: LoadSessionsUseCase,
    loadProgressionStateUseCase: LoadProgressionStateUseCase
) : ViewModel() {

    private val _eventChannel: Channel<DetailUiEvent> = Channel()
    val uiEventFlow: Flow<DetailUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val detailUiModel = combine(
        loadSessionsUseCase(),
        loadProgressionStateUseCase()
    ) { sessions: List<Session>, progressionState: ProgressionState ->
        val userProgression = when(progressionState) {
            is ProgressionState.None -> return@combine DetailUiState.Error
            is ProgressionState.OnGoing -> progressionState.currentUserProgression
            is ProgressionState.Done -> progressionState.latestUserProgression
        }

        return@combine DetailUiState.Result(
            userProgression = userProgression,
            sessions = sessions.filter { session ->
                session.isCompleted && session.progression == userProgression.toSessionProgression()
            }.sortedWith(compareBy { it.category.ordinal })
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), DetailUiState.Loading)

    fun onClickItem(sessionId: Long) {
        viewModelScope.launch {
            _eventChannel.send(DetailUiEvent.NavigateAccomplishment(sessionId))
        }
    }

}

sealed class DetailUiState {
    object Loading: DetailUiState()
    data class Result(val userProgression: UserProgression, val sessions: List<Session>): DetailUiState()
    object Error: DetailUiState()
}
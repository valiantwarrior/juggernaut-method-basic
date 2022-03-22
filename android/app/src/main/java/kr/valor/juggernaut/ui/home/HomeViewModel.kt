package kr.valor.juggernaut.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.common.HaltMethodStateContract
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.contract.SynchronizeSessionsContract
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionsUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionsUseCase
import javax.inject.Inject

sealed class HomeUiEvent {
    object HaltMethod: HomeUiEvent()
    data class NavigateSessionPreview(val sessionId: Long): HomeUiEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase,
    loadSessionsUseCase: LoadSessionsUseCase,
    private val synchronizeSessionsContract: SynchronizeSessionsContract,
    private val haltMethodStateContract: HaltMethodStateContract
) : ViewModel() {

    private val _eventChannel: Channel<HomeUiEvent> = Channel()
    val uiEventFlow: Flow<HomeUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val homeUiModel = combine(
        loadSessionsUseCase(),
        loadProgressionStateUseCase()
    ) { sessions: List<Session>, progressionState: ProgressionState ->

        val userProgression = when(progressionState) {
            is ProgressionState.None -> return@combine UiResult.Error
            is ProgressionState.OnGoing -> progressionState.currentUserProgression
            is ProgressionState.Done -> progressionState.latestUserProgression
        }

        return@combine UiResult.Success(
            userProgression = userProgression,
            sessions = sessions.filter { session ->
                session.progression == userProgression.toSessionProgression()
            }.sortedWith(compareBy<Session> { it.isCompleted }.thenBy { it.category.ordinal })
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), UiResult.Loading)

    init {
        viewModelScope.launch {
            synchronizeSessionsContract()
        }
    }

    fun onClickSessionItem(sessionId: Long) {
        viewModelScope.launch {
            _eventChannel.send(HomeUiEvent.NavigateSessionPreview(sessionId))
        }
    }

    fun onClickHalt() {
        viewModelScope.launch {
            haltMethodStateContract()
            _eventChannel.send(HomeUiEvent.HaltMethod)
        }
    }

}

sealed class UiResult {
    data class Success(val userProgression: UserProgression, val sessions: List<Session>): UiResult()
    object Error: UiResult()
    object Loading: UiResult()
}
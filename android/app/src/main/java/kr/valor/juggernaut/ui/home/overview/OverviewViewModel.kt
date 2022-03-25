package kr.valor.juggernaut.ui.home.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionsUseCase
import javax.inject.Inject

sealed class OverviewUiEvent {
    object HaltMethod: OverviewUiEvent()
    data class NavigatePreview(val sessionId: Long): OverviewUiEvent()
}

@HiltViewModel
class OverviewViewModel @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase,
    loadSessionsUseCase: LoadSessionsUseCase,
    private val synchronizeSessionsContract: SynchronizeSessionsContract,
    private val haltMethodStateContract: HaltMethodStateContract
) : ViewModel() {

    private val _eventChannel: Channel<OverviewUiEvent> = Channel()
    val uiEventFlow: Flow<OverviewUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val overviewUiModel = combine(
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
            _eventChannel.send(OverviewUiEvent.NavigatePreview(sessionId))
        }
    }

    fun onClickHalt() {
        viewModelScope.launch {
            haltMethodStateContract()
            _eventChannel.send(OverviewUiEvent.HaltMethod)
        }
    }
}

sealed class UiResult {
    data class Success(val userProgression: UserProgression, val sessions: List<Session>): UiResult()
    object Error: UiResult()
    object Loading: UiResult()
}
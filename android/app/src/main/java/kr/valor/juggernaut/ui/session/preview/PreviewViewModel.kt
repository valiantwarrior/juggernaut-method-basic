package kr.valor.juggernaut.ui.session.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionsUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import java.lang.IllegalStateException
import javax.inject.Inject

/**
 * TODO("Implementation change")
 *
 * Considering pass [Session] rather than passing sessionId
 *
 * (Dirty [Routine] objects are created.
 *
 * @see [DefaultSessionEntityMapper]
 *
 * @see [BasicMethodRoutineProviderDelegate]
 */

@HiltViewModel
class PreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionUseCase: FindSessionUseCase,
    loadSessionsUseCase: LoadSessionsUseCase,
    loadProgressionStateUseCase: LoadProgressionStateUseCase
): ViewModel() {

    private val _eventChannel: Channel<PreviewUiEvent> = Channel()
    val uiEventFlow: Flow<PreviewUiEvent>
        get() = _eventChannel.receiveAsFlow()

    val uiState: StateFlow<PreviewUiState>

    init {
        val totalCompletedSessionsCountFlow: Flow<Int> = flow {
            val userProgression = when(val progressionState = loadProgressionStateUseCase().first()) {
                is ProgressionState.OnGoing -> progressionState.currentUserProgression
                else -> throw IllegalStateException()
            }

            val completedSessionsCount = loadSessionsUseCase().map { sessions ->
                sessions.filter { session ->
                    session.sessionProgression == userProgression.toSessionProgression() && session.isCompleted
                }.size
            }.first()

            emit(completedSessionsCount)
        }

        uiState = combine(
            findSessionUseCase(sessionId = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!),
            totalCompletedSessionsCountFlow,
            ::Pair
        ).map { (session, totalCompletedSessionsCount) ->
            return@map PreviewUiState.Result(session, totalCompletedSessionsCount)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), PreviewUiState.Loading)
    }

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
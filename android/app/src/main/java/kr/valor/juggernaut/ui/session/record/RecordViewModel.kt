package kr.valor.juggernaut.ui.session.record

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionOneShotUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RecordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionOneShotUseCase: FindSessionOneShotUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            val sessionId: Long = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!
            val session = findSessionOneShotUseCase(sessionId)

            _uiState.value = when(session.progression.microCycle) {
                MicroCycle.DELOAD -> UiState.DeloadSession(session)
                else -> UiState.AmrapSession(
                    currentSession = session,
                    amrapRepetitions = session.amrapRoutine!!.reps
                )
            }
        }
    }

    fun onClickPlus() {
        acceptChangeWhenAmrapSession { amrapSession ->
            val changedRepetitions = amrapSession.amrapRepetitions + 1
            _uiState.value = amrapSession.copy(amrapRepetitions = changedRepetitions)
        }
    }

    fun onClickMinus() {
        acceptChangeWhenAmrapSession { amrapSession ->
            val changedRepetitions = amrapSession.amrapRepetitions - 1
            if (changedRepetitions < 0) {
                return@acceptChangeWhenAmrapSession
            } else {
                _uiState.value = amrapSession.copy(amrapRepetitions = changedRepetitions)
            }
        }
    }

    private inline fun acceptChangeWhenAmrapSession(change: (UiState.AmrapSession) -> Unit) {
        when(val currentUiState = uiState.value) {
            is UiState.AmrapSession -> change(currentUiState)
            else -> return
        }
    }

}

sealed class UiState {
    object Loading: UiState()
    data class AmrapSession(
        val currentSession: Session,
        val amrapRepetitions: Int
    ): UiState()
    data class DeloadSession(
        val currentSession: Session
    ): UiState()
}
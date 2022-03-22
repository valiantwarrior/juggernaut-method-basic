package kr.valor.juggernaut.ui.session.record

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.Session.Progression.Companion.DELOAD_SESSION_INDICATOR
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_BASE_AMRAP_REPETITIONS_KEY
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionUseCase: FindSessionUseCase
) : ViewModel() {

    val uiState: StateFlow<RecordUiState> = findSessionUseCase(sessionId = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!)
        .map { session ->
            when(session.progression.isAmrapSession) {
                true -> RecordUiState.AmrapSession(session)
                false -> RecordUiState.DeloadSession(session)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), initialValue = RecordUiState.Loading)

    private val _amrapSessionBaseRepetitions: MutableStateFlow<Int>? =
        when(val baseRepetitions = savedStateHandle.get<Int>(NAV_ARGS_BASE_AMRAP_REPETITIONS_KEY)!!) {
            DELOAD_SESSION_INDICATOR -> null
            else -> MutableStateFlow(baseRepetitions)
        }
    val amrapSessionBaseRepetitions: StateFlow<Int>?
        get() = _amrapSessionBaseRepetitions

    val accept: (RecordUiAction) -> Unit

    init {
        accept = { recordUiAction ->
            when(recordUiAction) {
                RecordUiAction.Plus -> {
                    _amrapSessionBaseRepetitions.acceptResultWhenNotNull { currentRepetitions ->
                        currentRepetitions + 1
                    }
                }
                RecordUiAction.Minus -> {
                    _amrapSessionBaseRepetitions.acceptResultWhenNotNull { currentRepetitions ->
                        val nextRepetitions = currentRepetitions - 1
                        if (nextRepetitions < 0) null else nextRepetitions
                    }
                }
            }
        }
    }

    private inline fun <T> MutableStateFlow<T>?.acceptResultWhenNotNull(produce: (T) -> T?) {
        this?.let { stateFlow ->
            val result = produce(stateFlow.value)
            if (result == null) {
                return@let
            } else {
                stateFlow.value = result
            }
        }
    }
}

sealed class RecordUiAction {
    object Plus: RecordUiAction()
    object Minus: RecordUiAction()
}

sealed class RecordUiState {
    object Loading: RecordUiState()
    data class AmrapSession(val currentSession: Session): RecordUiState()
    data class DeloadSession(val currentSession: Session): RecordUiState()
}
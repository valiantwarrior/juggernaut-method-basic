package kr.valor.juggernaut.ui.session.accomplishment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionOneShotUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import javax.inject.Inject

@HiltViewModel
class AccomplishmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionOneShotUseCase: FindSessionOneShotUseCase
) : ViewModel() {

    val uiState: StateFlow<AccomplishmentUiState> = flow {
        val sessionId: Long = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!
        val session = findSessionOneShotUseCase(sessionId)

        emit(AccomplishmentUiState.Result(session))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), AccomplishmentUiState.Loading)

}

sealed class AccomplishmentUiState {
    object Loading: AccomplishmentUiState()
    data class Result(val session: Session): AccomplishmentUiState()
}
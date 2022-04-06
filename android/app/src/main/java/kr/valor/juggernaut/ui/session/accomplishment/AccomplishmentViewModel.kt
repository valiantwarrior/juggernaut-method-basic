package kr.valor.juggernaut.ui.session.accomplishment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionByIdOneShotUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_ACCOMPLISHMENT_DESTINATION_TOKEN
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import kr.valor.juggernaut.ui.common.WhileViewSubscribed
import javax.inject.Inject

@HiltViewModel
class AccomplishmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionByIdOneShotUseCase: FindSessionByIdOneShotUseCase
): ViewModel() {

    val uiState = flow {
        findSessionByIdOneShotUseCase(
            sessionId = savedStateHandle[NAV_ARGS_SESSION_ID_KEY]!!
        ).also { emit(it) }
    }.map { session ->
        val token: AccomplishmentDestinationToken = savedStateHandle[NAV_ARGS_ACCOMPLISHMENT_DESTINATION_TOKEN]!!

        return@map AccomplishmentUiState.Result(
            session = session,
            destinationToken = token
        )
    }.stateIn(viewModelScope, WhileViewSubscribed, AccomplishmentUiState.Loading)

}

sealed class AccomplishmentUiState {
    data class Result(
        val session: Session,
        val destinationToken: AccomplishmentDestinationToken
    ): AccomplishmentUiState()
    object Loading: AccomplishmentUiState()
}
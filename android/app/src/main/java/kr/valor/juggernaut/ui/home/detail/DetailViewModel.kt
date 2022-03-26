package kr.valor.juggernaut.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.usecase.usecase.LoadSessionsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    loadSessionsUseCase: LoadSessionsUseCase,
    loadProgressionStateUseCase: LoadProgressionStateUseCase
) : ViewModel() {

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

}

sealed class DetailUiState {
    object Loading: DetailUiState()
    data class Result(val userProgression: UserProgression, val sessions: List<Session>): DetailUiState()
    object Error: DetailUiState()
}
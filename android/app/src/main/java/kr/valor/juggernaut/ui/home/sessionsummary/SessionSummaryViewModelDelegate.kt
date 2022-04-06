package kr.valor.juggernaut.ui.home.sessionsummary

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionIdsByUserProgressionUseCase
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionSummaryByIdOneShotUseCase
import javax.inject.Inject

interface SessionSummaryViewModelDelegate {

    val userProgressionWithSessionSummaries: Flow<Pair<UserProgression, List<SessionSummary>>>

}

@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class SessionSummaryViewModelDelegateImpl @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase,
    findSessionIdsByUserProgressionUseCase: FindSessionIdsByUserProgressionUseCase,
    findSessionSummaryByIdOneShotUseCase: FindSessionSummaryByIdOneShotUseCase
): SessionSummaryViewModelDelegate {

    private val userProgression: Flow<UserProgression> =
        loadProgressionStateUseCase().map { progressionState ->
            when(progressionState) {
                is ProgressionState.None -> throw IllegalStateException()
                is ProgressionState.OnGoing -> progressionState.currentUserProgression
                is ProgressionState.Done -> progressionState.latestUserProgression
            }
        }

    private val sessionIds: Flow<List<Long>> =
        userProgression.flatMapLatest { userProgression ->
            findSessionIdsByUserProgressionUseCase(userProgression)
        }.distinctUntilChanged()

    override val userProgressionWithSessionSummaries: Flow<Pair<UserProgression, List<SessionSummary>>> =
        combine(userProgression, sessionIds) { userProgression, sessionIds ->
            userProgression to sessionIds.map { findSessionSummaryByIdOneShotUseCase(it) }
        }

}
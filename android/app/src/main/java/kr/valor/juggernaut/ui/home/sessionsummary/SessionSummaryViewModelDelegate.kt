package kr.valor.juggernaut.ui.home.sessionsummary

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionSummariesUseCase
import javax.inject.Inject

interface SessionSummaryViewModelDelegate {

    val sessionSummaryState: Flow<Pair<UserProgression, List<SessionSummary>>>

}

@ExperimentalCoroutinesApi
class SessionSummaryViewModelDelegateImpl @Inject constructor(
    loadProgressionStateUseCase: LoadProgressionStateUseCase,
    findSessionSummariesUseCase: FindSessionSummariesUseCase
): SessionSummaryViewModelDelegate {

    private val userProgression: Flow<UserProgression> =
        loadProgressionStateUseCase().map { progressionState ->
            when(progressionState) {
                is ProgressionState.None -> throw IllegalStateException()
                is ProgressionState.OnGoing -> progressionState.currentUserProgression
                is ProgressionState.Done -> progressionState.latestUserProgression
            }
        }

    private val sessionSummaries: Flow<List<SessionSummary>> =
        userProgression.flatMapLatest { userProgression ->
            findSessionSummariesUseCase(userProgression)
        }

    override val sessionSummaryState: Flow<Pair<UserProgression, List<SessionSummary>>> =
        combine(userProgression, sessionSummaries) { userProgression, sessionSummaries ->
            userProgression to sessionSummaries
        }

}
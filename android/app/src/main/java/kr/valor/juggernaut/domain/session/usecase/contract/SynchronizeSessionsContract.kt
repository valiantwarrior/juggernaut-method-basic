package kr.valor.juggernaut.domain.session.usecase.contract

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.trainingmax.usecase.FindUserTrainingMaxesByUserProgressionUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.GetProgressionStateUseCase

interface SynchronizeSessionsContract {

    suspend operator fun invoke()

}

class SynchronizeSessionsContractImpl(
    private val repository: SessionRepository,
    private val getProgressionStateUseCase: GetProgressionStateUseCase,
    private val findUserTrainingMaxesByUserProgressionUseCase: FindUserTrainingMaxesByUserProgressionUseCase
): SynchronizeSessionsContract {

    override suspend fun invoke() {
        when(val currentProgressionSate = getProgressionStateUseCase().first()) {
            is ProgressionState.None -> return
            is ProgressionState.OnGoing -> {
                synchronizeSessionsWithCurrentUserProgression(currentProgressionSate.currentUserProgression)
            }
            is ProgressionState.Done -> {
                synchronizeSessionsWithCurrentUserProgression(currentProgressionSate.latestUserProgression)
            }
        }
    }

    private suspend fun synchronizeSessionsWithCurrentUserProgression(userProgression: UserProgression) {
        val userTrainingMaxes = findUserTrainingMaxesByUserProgressionUseCase(userProgression)
        repository.synchronizeSessions(userProgression, userTrainingMaxes)
    }

}
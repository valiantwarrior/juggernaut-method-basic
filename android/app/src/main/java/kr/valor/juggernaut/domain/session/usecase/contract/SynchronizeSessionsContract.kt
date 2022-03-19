package kr.valor.juggernaut.domain.session.usecase.contract

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.trainingmax.usecase.FindTrainingMaxesUseCase
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import javax.inject.Inject

class SynchronizeSessionsContract @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val loadProgressionStateUseCase: LoadProgressionStateUseCase,
    private val findTrainingMaxesUseCase: FindTrainingMaxesUseCase
) {

    suspend operator fun invoke() {
        when(val currentProgressionSate = loadProgressionStateUseCase().first()) {
            ProgressionState.None -> return
            is ProgressionState.OnGoing -> {
                synchronizeSessionsWithCurrentUserProgression(currentProgressionSate.currentUserProgression)
            }
            is ProgressionState.Done -> {
                synchronizeSessionsWithCurrentUserProgression(currentProgressionSate.latestUserProgression)
            }
        }
    }

    private suspend fun synchronizeSessionsWithCurrentUserProgression(userProgression: UserProgression) {
        val userTrainingMaxes = findTrainingMaxesUseCase(userProgression)
        sessionRepository.synchronizeSessions(userProgression, userTrainingMaxes)
    }

}
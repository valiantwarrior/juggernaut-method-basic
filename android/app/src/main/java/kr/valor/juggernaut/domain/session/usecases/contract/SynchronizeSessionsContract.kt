package kr.valor.juggernaut.domain.session.usecases.contract

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.usecases.usecase.FindUserTrainingMaxesByUserProgressionUseCase
import kr.valor.juggernaut.domain.user.usecases.usecase.GetUserProgressionUseCase

interface SynchronizeSessionsContract {

    suspend operator fun invoke()

}

class SynchronizeSessionsContractImpl(
    private val repository: SessionRepository,
    private val getUserProgressionUseCase: GetUserProgressionUseCase,
    private val findUserTrainingMaxesByUserProgressionUseCase: FindUserTrainingMaxesByUserProgressionUseCase
): SynchronizeSessionsContract {

    override suspend fun invoke() {
        val userProgression = getUserProgressionUseCase().first()
        val userTrainingMaxes = findUserTrainingMaxesByUserProgressionUseCase(userProgression)

        repository.synchronizeSessions(userProgression, userTrainingMaxes)
    }

}
package kr.valor.juggernaut.domain.session.usecases

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.repository.UserRepository

interface SynchronizeSessionUseCase {
    suspend operator fun invoke()
}

class SynchronizeSessionUseCaseImpl(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository
): SynchronizeSessionUseCase {
    override suspend fun invoke() {
        val currentUserProgression = userRepository.getUserProgression().first()
        val currentUserTm = userRepository.getUserTrainingMax(currentUserProgression.liftCategory)

        sessionRepository.synchronizeSession(currentUserProgression, currentUserTm)
    }
}

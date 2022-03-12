package kr.valor.juggernaut.domain.session.usecases

import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.usecases.GetUserProgressionUseCase
import kr.valor.juggernaut.domain.user.usecases.FindUserTrainingMaxByLiftCategoryUseCase

interface SynchronizeSessionUseCase {
    suspend operator fun invoke()
}

class SynchronizeSessionUseCaseImpl(
    private val sessionRepository: SessionRepository,
    private val getUserProgressionUseCase: GetUserProgressionUseCase,
    private val findUserTrainingMaxByLiftCategoryUseCase: FindUserTrainingMaxByLiftCategoryUseCase
): SynchronizeSessionUseCase {
    override suspend fun invoke() {
        val userProgression = getUserProgressionUseCase().first()
        val userTrainingMax = findUserTrainingMaxByLiftCategoryUseCase(userProgression.liftCategory)
        sessionRepository.synchronizeSession(userProgression, userTrainingMax)
    }
}

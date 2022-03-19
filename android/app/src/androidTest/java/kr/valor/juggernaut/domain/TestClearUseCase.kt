package kr.valor.juggernaut.domain

import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import javax.inject.Inject

class TestClearUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val trainingMaxRepository: TrainingMaxRepository,
    private val progressionStateRepository: ProgressionStateRepository
) {

    suspend operator fun invoke() {
        sessionRepository.clear()
        trainingMaxRepository.clear()
        progressionStateRepository.clear()
    }

}
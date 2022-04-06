package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class CountCompletedSessionsBasedOnUserProgressionUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(userProgression: UserProgression) =
        repository.countCompletedSessionEntitiesBasedOnUserProgression(userProgression)

}
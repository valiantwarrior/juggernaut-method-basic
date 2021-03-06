package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class FindSessionIdsByUserProgressionUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    operator fun invoke(userProgression: UserProgression) =
        repository.findSessionIdsByUserProgression(userProgression)

}
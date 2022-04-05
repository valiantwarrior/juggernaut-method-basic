package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class FindSessionSummariesUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    operator fun invoke(userProgression: UserProgression) =
        repository.findSessionSummariesByUserProgression(userProgression)

}
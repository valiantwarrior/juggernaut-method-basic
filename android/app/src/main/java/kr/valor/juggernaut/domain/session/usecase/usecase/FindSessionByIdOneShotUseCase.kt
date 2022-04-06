package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class FindSessionByIdOneShotUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(sessionId: Long) =
        repository.findSessionByIdOneShot(sessionId)

}
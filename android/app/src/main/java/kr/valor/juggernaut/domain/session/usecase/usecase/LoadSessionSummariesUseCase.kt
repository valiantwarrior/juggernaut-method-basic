package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class LoadSessionSummariesUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    operator fun invoke() = repository.getAllSessionSummaries()

}
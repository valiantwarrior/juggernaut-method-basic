package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class DeleteSessionsUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(methodCycle: MethodCycle) {
        repository.deleteSessionsByMethodCycle(methodCycle)
    }

}
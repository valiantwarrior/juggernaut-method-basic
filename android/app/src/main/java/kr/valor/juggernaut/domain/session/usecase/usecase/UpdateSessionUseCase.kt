package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import javax.inject.Inject

class UpdateSessionUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(session: Session, sessionRecord: SessionRecord) {
        repository.updateSession(session, sessionRecord)
    }

}
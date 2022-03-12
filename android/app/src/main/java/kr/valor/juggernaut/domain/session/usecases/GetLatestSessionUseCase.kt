package kr.valor.juggernaut.domain.session.usecases

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.session.model.Session

typealias GetLatestSessionUseCase = () -> Flow<Session>
package kr.valor.juggernaut.domain.session.usecase

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.session.model.Session

typealias GetAllSessionsUseCase = () -> Flow<List<Session>>
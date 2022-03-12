package kr.valor.juggernaut.domain.session.usecases

import kr.valor.juggernaut.domain.session.model.Session

typealias GetSessionByIdUseCase = suspend (Long) -> Session
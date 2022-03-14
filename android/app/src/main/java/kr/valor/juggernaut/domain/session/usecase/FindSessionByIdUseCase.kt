package kr.valor.juggernaut.domain.session.usecase

import kr.valor.juggernaut.domain.session.model.Session

typealias FindSessionByIdUseCase = suspend (Long) -> Session
package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.domain.session.model.Session

typealias FindSessionByIdUseCase = suspend (Long) -> Session
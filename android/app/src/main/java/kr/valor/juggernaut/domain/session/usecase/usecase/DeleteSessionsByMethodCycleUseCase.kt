package kr.valor.juggernaut.domain.session.usecase.usecase

import kr.valor.juggernaut.common.MethodCycle

typealias DeleteSessionsByMethodCycleUseCase = suspend (MethodCycle) -> Unit
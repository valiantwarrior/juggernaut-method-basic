package kr.valor.juggernaut.domain.user.usecases.usecase

import kr.valor.juggernaut.common.MethodCycle

typealias UpdateMethodCycleStateUseCase = suspend (MethodCycle) -> Unit
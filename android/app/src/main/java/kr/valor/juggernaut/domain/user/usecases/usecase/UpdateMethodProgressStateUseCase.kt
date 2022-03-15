package kr.valor.juggernaut.domain.user.usecases.usecase

import kr.valor.juggernaut.common.MethodProgressState

typealias UpdateMethodProgressStateUseCase = suspend (MethodProgressState) -> Unit
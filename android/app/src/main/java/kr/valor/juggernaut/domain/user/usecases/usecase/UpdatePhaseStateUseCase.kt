package kr.valor.juggernaut.domain.user.usecases.usecase

import kr.valor.juggernaut.common.Phase

typealias UpdatePhaseStateUseCase = suspend (Phase) -> Unit
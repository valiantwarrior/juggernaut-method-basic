package kr.valor.juggernaut.domain.user.usecases.usecase

import kr.valor.juggernaut.common.MicroCycle

typealias UpdateMicroCycleStateUseCase = suspend (MicroCycle) -> Unit
package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.common.MethodCycle

typealias DeleteUserTrainingMaxesByMethodCycleUseCase = suspend (MethodCycle) -> Unit
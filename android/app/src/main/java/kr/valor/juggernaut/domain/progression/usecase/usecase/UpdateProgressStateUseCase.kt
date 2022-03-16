package kr.valor.juggernaut.domain.progression.usecase.usecase

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

typealias UpdateMethodProgressStateUseCase = suspend (MethodProgressState) -> Unit

typealias UpdateMethodCycleStateUseCase = suspend (MethodCycle) -> Unit

typealias UpdateMicroCycleStateUseCase = suspend (MicroCycle) -> Unit

typealias UpdatePhaseStateUseCase = suspend (Phase) -> Unit
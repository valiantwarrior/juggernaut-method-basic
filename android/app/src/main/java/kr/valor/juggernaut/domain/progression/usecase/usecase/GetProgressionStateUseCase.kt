package kr.valor.juggernaut.domain.progression.usecase.usecase

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.progression.model.ProgressionState

typealias GetProgressionStateUseCase = () -> Flow<ProgressionState>
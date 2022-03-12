package kr.valor.juggernaut.domain.user.usecases

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.user.model.UserProgression

typealias GetUserProgressionUseCase = suspend () -> Flow<UserProgression>

package kr.valor.juggernaut.domain.user.usecases.usecase

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.user.model.UserProgression

typealias GetUserProgressionUseCase = () -> Flow<UserProgression>
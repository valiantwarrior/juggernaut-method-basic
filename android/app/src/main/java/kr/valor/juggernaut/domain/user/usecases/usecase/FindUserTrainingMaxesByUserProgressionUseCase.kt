package kr.valor.juggernaut.domain.user.usecases.usecase

import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

typealias FindUserTrainingMaxesByUserProgressionUseCase = suspend (UserProgression) -> List<UserTrainingMax>
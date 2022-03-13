package kr.valor.juggernaut.domain.user.usecases

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

typealias FindUserTrainingMaxByLiftCategoryUseCase = suspend (LiftCategory) -> UserTrainingMax
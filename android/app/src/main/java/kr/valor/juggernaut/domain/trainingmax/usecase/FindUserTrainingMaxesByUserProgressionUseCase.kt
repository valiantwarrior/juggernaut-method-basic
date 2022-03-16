package kr.valor.juggernaut.domain.trainingmax.usecase

import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax

typealias FindUserTrainingMaxesByUserProgressionUseCase = suspend (UserProgression) -> List<TrainingMax>
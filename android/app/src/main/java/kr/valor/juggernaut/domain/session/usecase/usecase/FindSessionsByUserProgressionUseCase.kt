package kr.valor.juggernaut.domain.session.usecase.usecase

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.progression.model.UserProgression

typealias FindSessionsByUserProgression = (UserProgression) -> Flow<List<Session>>
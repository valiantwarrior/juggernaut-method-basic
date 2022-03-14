package kr.valor.juggernaut.domain.session.usecase

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.user.model.UserProgression

typealias FindSessionsByUserProgression = (UserProgression) -> Flow<List<Session>>
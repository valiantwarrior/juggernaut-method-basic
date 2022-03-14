package kr.valor.juggernaut.domain.user.usecase

import kr.valor.juggernaut.common.ProgressionElement

typealias UpdateUserProgressionUseCase = suspend (ProgressionElement) -> Unit
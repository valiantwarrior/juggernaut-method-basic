package kr.valor.juggernaut.domain.user.usecases.usecase

import kr.valor.juggernaut.common.ProgressionElement

typealias UpdateUserProgressionUseCase = suspend (ProgressionElement) -> Unit
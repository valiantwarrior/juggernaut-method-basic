package kr.valor.juggernaut.ui.common

import kotlinx.coroutines.flow.SharingStarted

private const val StopTimeoutMillis: Long = 5000L

val WhileViewSubscribed: SharingStarted = SharingStarted.WhileSubscribed(StopTimeoutMillis)
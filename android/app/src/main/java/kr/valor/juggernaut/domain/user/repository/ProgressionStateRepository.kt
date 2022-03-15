package kr.valor.juggernaut.domain.user.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.ProgressionElement
import kr.valor.juggernaut.domain.user.model.ProgressionState
import kr.valor.juggernaut.domain.user.model.UserProgression

interface ProgressionStateRepository {
    fun getProgressionState(): Flow<ProgressionState>
    suspend fun updateUserProgression(element: ProgressionElement)
    suspend fun updateMethodProgressState(methodProgressState: MethodProgressState)
    suspend fun clear()
}
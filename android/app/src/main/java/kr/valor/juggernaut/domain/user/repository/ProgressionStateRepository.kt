package kr.valor.juggernaut.domain.user.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.user.model.ProgressionState
import kr.valor.juggernaut.domain.user.model.UserProgression

interface ProgressionStateRepository {
    fun getProgressionState(): Flow<ProgressionState>
    suspend fun updateMethodCycleState(methodCycle: MethodCycle)
    suspend fun updatePhaseState(phase: Phase)
    suspend fun updateMicroCycleState(microCycle: MicroCycle)
    suspend fun updateMethodProgressState(methodProgressState: MethodProgressState)
    suspend fun clear()
}
package kr.valor.juggernaut.domain.progression.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.progression.model.ProgressionState

interface ProgressionStateRepository {
    fun getProgressionState(): Flow<ProgressionState>
    suspend fun updateMethodCycleState(methodCycle: MethodCycle)
    suspend fun updatePhaseState(phase: Phase)
    suspend fun updateMicroCycleState(microCycle: MicroCycle)
    suspend fun updateMethodProgressState(methodProgressState: MethodProgressState)
    suspend fun clear()
}
package kr.valor.juggernaut.data.progression.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.progression.model.ProgressionState

interface ProgressionStateDataSource {

    fun getProgressionStateData(): Flow<ProgressionState>

    suspend fun editMethodCycleState(methodCycle: MethodCycle)

    suspend fun editPhaseState(phase: Phase)

    suspend fun editMicroCycleState(microCycle: MicroCycle)

    suspend fun editMethodProgressState(methodProgressState: MethodProgressState)

    suspend fun clear()

}
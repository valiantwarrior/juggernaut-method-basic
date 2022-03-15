package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.user.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.user.model.ProgressionState
import kr.valor.juggernaut.domain.user.repository.ProgressionStateRepository

class DefaultProgressionStateRepository(
    private val progressionStateDataSource: ProgressionStateDataSource
): ProgressionStateRepository {

    override fun getProgressionState(): Flow<ProgressionState> =
        progressionStateDataSource.getProgressionStateData()

    override suspend fun updateMethodCycleState(methodCycle: MethodCycle) {
        progressionStateDataSource.editMethodCycleState(methodCycle)
    }

    override suspend fun updatePhaseState(phase: Phase) {
        progressionStateDataSource.editPhaseState(phase)
    }

    override suspend fun updateMicroCycleState(microCycle: MicroCycle) {
        progressionStateDataSource.editMicroCycleState(microCycle)
    }

    override suspend fun updateMethodProgressState(methodProgressState: MethodProgressState) {
        if (methodProgressState == MethodProgressState.NONE) {
            return // in this case, call clear() instead
        } else {
            progressionStateDataSource.editMethodProgressState(methodProgressState)
        }
    }

    override suspend fun clear() {
        progressionStateDataSource.clear()
    }
}
package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.progression.di.DataStoreSource
import kr.valor.juggernaut.data.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultProgressionStateRepository @Inject constructor(
    @DataStoreSource private val progressionStateDataSource: ProgressionStateDataSource
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
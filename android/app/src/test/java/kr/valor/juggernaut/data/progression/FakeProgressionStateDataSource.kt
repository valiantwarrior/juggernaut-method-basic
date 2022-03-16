package kr.valor.juggernaut.data.progression

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.model.UserProgression

class FakeProgressionStateDataSource: ProgressionStateDataSource {

    private val methodProgressionState = MutableStateFlow(MethodProgressState.NONE)

    private val userProgressionInitial = UserProgression(
        MethodCycle(MethodCycle.INITIAL_VALUE), Phase.INITIAL, MicroCycle.INITIAL
    )

    private val userProgression = MutableStateFlow(
        userProgressionInitial
    )

    override fun getProgressionStateData(): Flow<ProgressionState> =
        combine(methodProgressionState, userProgression) { methodProgressState, userProgression ->
            when(methodProgressState) {
                MethodProgressState.NONE -> ProgressionState.None
                MethodProgressState.ONGOING -> ProgressionState.OnGoing(userProgression)
                MethodProgressState.DONE -> ProgressionState.Done(userProgression)
            }
        }

    override suspend fun editMethodCycleState(methodCycle: MethodCycle) {
        userProgression.value = userProgression.value.copy(methodCycle = methodCycle)
    }

    override suspend fun editPhaseState(phase: Phase) {
        userProgression.value = userProgression.value.copy(phase = phase)
    }

    override suspend fun editMicroCycleState(microCycle: MicroCycle) {
        userProgression.value = userProgression.value.copy(microCycle = microCycle)
    }

    override suspend fun editMethodProgressState(methodProgressState: MethodProgressState) {
        methodProgressionState.value = methodProgressState
    }

    override suspend fun clear() {
        methodProgressionState.value = MethodProgressState.NONE
        userProgression.value = userProgressionInitial
    }

}
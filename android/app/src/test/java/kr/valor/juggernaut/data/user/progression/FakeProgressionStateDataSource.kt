package kr.valor.juggernaut.data.user.progression

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.user.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.user.model.ProgressionState
import kr.valor.juggernaut.domain.user.model.UserProgression

class FakeProgressionStateDataSource: ProgressionStateDataSource {

    private val methodProgressionState = MutableStateFlow(MethodProgressState.NONE)

    private val userProgressionInitial = UserProgression(
        MethodCycle(MethodCycle.INITIAL), Phase.INITIAL, MicroCycle.INITIAL
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

    override suspend fun editMethodProgressState(methodProgressState: MethodProgressState) {
        methodProgressionState.value = methodProgressState
    }

    override suspend fun editUserProgression(progressionElement: ProgressionElement) {
        userProgression.value = with(userProgression.value) {
            when(progressionElement) {
                is MethodCycle -> copy(methodCycle = progressionElement)
                is Phase -> copy(phase = progressionElement)
                is MicroCycle -> copy(microCycle = progressionElement)
            }
        }
    }

    override suspend fun clear() {
        methodProgressionState.value = MethodProgressState.NONE
        userProgression.value = userProgressionInitial
    }

}
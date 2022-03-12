package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.user.model.UserProgression

class FakeUserProgressionDataSource: UserProgressionDataSource {

    private val initialState = UserProgression(
        MethodCycle(1), Phase.REP10, MicroCycle.ACCUMULATION, LiftCategory.BENCH_PRESS
    )

    // mutable state flow UserProgression
    private val _userProgressionStateFlow: MutableStateFlow<UserProgression> =
        MutableStateFlow(initialState)

    override fun getUserProgressionData(): Flow<UserProgression> =
        flowOf(_userProgressionStateFlow.value)

    override suspend fun editUserProgression(progressionElement: ProgressionElement) {
        when(progressionElement) {
            is MethodCycle -> editMethodCyclePreference(progressionElement)
            is Phase -> editPhasePreference(progressionElement)
            is MicroCycle -> editMicroCyclePreference(progressionElement)
            is LiftCategory -> editLiftCategoryPreference(progressionElement)
        }
    }

    private fun editMethodCyclePreference(methodCycle: MethodCycle) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(methodCycle = methodCycle) }
    }

    private fun editMicroCyclePreference(microCycle: MicroCycle) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(microCycle = microCycle) }
    }

    private fun editPhasePreference(phase: Phase) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(phase = phase) }
    }

    private fun editLiftCategoryPreference(liftCategory: LiftCategory) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(liftCategory = liftCategory) }
    }

    override suspend fun clear() {
        _userProgressionStateFlow.update { initialState }
    }
}
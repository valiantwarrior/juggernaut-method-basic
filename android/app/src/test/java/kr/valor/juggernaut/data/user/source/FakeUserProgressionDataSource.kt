package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.model.UserProgression

class FakeUserProgressionDataSource: UserProgressionDataSource {

    private val initialState = UserProgression(
        1, Phase.REP10, MicroCycle.ACCUMULATION, LiftCategory.BENCH_PRESS
    )

    // mutable state flow UserProgression
    private val _userProgressionStateFlow: MutableStateFlow<UserProgression> =
        MutableStateFlow(initialState)

    override fun getUserProgressionData(): Flow<UserProgression> =
        flowOf(_userProgressionStateFlow.value)

    override suspend fun editUserProgression(methodCycle: Int) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(methodCycle = methodCycle) }
    }

    override suspend fun editUserProgression(microCycle: MicroCycle) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(microCycle = microCycle) }
    }

    override suspend fun editUserProgression(phase: Phase) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(phase = phase) }
    }

    override suspend fun editUserProgression(liftCategory: LiftCategory) {
        val current = _userProgressionStateFlow.value
        _userProgressionStateFlow.update { current.copy(liftCategory = liftCategory) }
    }

    override suspend fun clear() {
        _userProgressionStateFlow.update { initialState }
    }
}
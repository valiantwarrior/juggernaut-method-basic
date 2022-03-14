package kr.valor.juggernaut.data.user.progression

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.user.progression.source.UserProgressionDataSource
import kr.valor.juggernaut.domain.user.model.UserProgression

class FakeUserProgressionDataSource: UserProgressionDataSource {

    private val initialState = UserProgression(
        MethodCycle(1), Phase.REP10, MicroCycle.ACCUMULATION
    )

    private lateinit var inMemoryUserProgression: UserProgression

    override fun getUserProgressionData(): Flow<UserProgression> =
        flowOf(inMemoryUserProgression)

    override suspend fun editUserProgression(progressionElement: ProgressionElement) {
        inMemoryUserProgression = when(progressionElement) {
            is MethodCycle -> {
                inMemoryUserProgression.copy(methodCycle = progressionElement)
            }
            is Phase -> {
                inMemoryUserProgression.copy(phase = progressionElement)
            }
            is MicroCycle -> {
                inMemoryUserProgression.copy(microCycle = progressionElement)
            }
        }
    }

    override suspend fun clear() {
        inMemoryUserProgression = initialState
    }

}
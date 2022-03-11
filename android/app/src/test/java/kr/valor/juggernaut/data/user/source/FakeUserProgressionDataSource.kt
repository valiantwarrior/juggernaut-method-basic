package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.model.UserProgression

class FakeUserProgressionDataSource: UserProgressionDataSource {

    // mutable state flow UserProgression

    override fun getUserProgressionData(): Flow<UserProgression> {
        TODO("Not yet implemented")
    }

    override suspend fun editUserProgression(methodCycle: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun editUserProgression(microCycle: MicroCycle) {
        TODO("Not yet implemented")
    }

    override suspend fun editUserProgression(phase: Phase) {
        TODO("Not yet implemented")
    }

    override suspend fun editUserProgression(liftCategory: LiftCategory) {
        TODO("Not yet implemented")
    }
}
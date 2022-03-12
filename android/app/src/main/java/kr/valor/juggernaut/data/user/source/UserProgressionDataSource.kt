package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.model.UserProgression

interface UserProgressionDataSource {
    fun getUserProgressionData(): Flow<UserProgression>
    suspend fun editUserProgression(methodCycle: Int)
    suspend fun editUserProgression(microCycle: MicroCycle)
    suspend fun editUserProgression(phase: Phase)
    suspend fun editUserProgression(liftCategory: LiftCategory)
    suspend fun clear()
}
package kr.valor.juggernaut.data.user.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.domain.user.model.UserProgression

interface UserProgressionDataSource {
    fun getUserProgressionData(): Flow<UserProgression>
    suspend fun editUserProgression(progressionElement: ProgressionElement)
    suspend fun clear()
}
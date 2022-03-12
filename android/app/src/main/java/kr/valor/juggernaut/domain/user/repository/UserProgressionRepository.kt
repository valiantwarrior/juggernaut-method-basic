package kr.valor.juggernaut.domain.user.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.ProgressionElement
import kr.valor.juggernaut.domain.user.model.UserProgression

interface UserProgressionRepository {
    fun getUserProgression(): Flow<UserProgression>
    suspend fun updateUserProgression(element: ProgressionElement)
    suspend fun clear()
}
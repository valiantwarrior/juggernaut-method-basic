package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.user.progression.source.UserProgressionDataSource
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.repository.UserProgressionRepository

class DefaultUserProgressionRepository(
    private val userProgressionDataSource: UserProgressionDataSource
): UserProgressionRepository {

    override fun getUserProgression(): Flow<UserProgression> =
        userProgressionDataSource.getUserProgressionData()

    override suspend fun updateUserProgression(element: ProgressionElement) {
        userProgressionDataSource.editUserProgression(element)
    }

    override suspend fun clear() {
        userProgressionDataSource.clear()
    }
}
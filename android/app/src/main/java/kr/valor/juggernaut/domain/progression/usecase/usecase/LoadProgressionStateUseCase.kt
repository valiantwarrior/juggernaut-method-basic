package kr.valor.juggernaut.domain.progression.usecase.usecase

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject

class LoadProgressionStateUseCase @Inject constructor(
    private val repository: ProgressionStateRepository
) {

    operator fun invoke(): Flow<ProgressionState> =
        repository.getProgressionState()

}
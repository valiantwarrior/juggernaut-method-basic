package kr.valor.juggernaut.domain.progression.usecase.usecase

import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import javax.inject.Inject

class ClearProgressionStateUseCase @Inject constructor(
    private val repository: ProgressionStateRepository
) {

    suspend operator fun invoke() {
        repository.clear()
    }

}
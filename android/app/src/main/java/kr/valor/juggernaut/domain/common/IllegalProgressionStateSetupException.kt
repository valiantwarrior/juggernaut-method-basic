package kr.valor.juggernaut.domain.common

class IllegalProgressionStateSetupException(override val message: String): Exception(message) {
    companion object {
        const val START_ERROR_MESSAGE = "A new method cannot be started if the current state is ProgressionState.OnGoing."
        const val HALT_ERROR_MESSAGE = "Cannot halt current method because there is no method currently in progress."
    }
}
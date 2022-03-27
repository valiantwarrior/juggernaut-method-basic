package kr.valor.juggernaut.ui.session

import kr.valor.juggernaut.domain.session.model.Routine

sealed class SessionRoutineItem {

    abstract val itemId: Long

}

open class RoutineItem(
    open val routineOrdinal: Int,
    open val routine: Routine,
    open val isDeloadRoutine: Boolean
): SessionRoutineItem() {

    override val itemId: Long
        get() = routine.routineId

    override fun equals(other: Any?): Boolean =
        (other as RoutineItem).routine == this.routine &&
                other.routineOrdinal == this.routineOrdinal &&
                other.isDeloadRoutine == this.isDeloadRoutine

    override fun hashCode(): Int {
        return routine.hashCode()
    }

}

data class FooterItem(val buttonText: String): SessionRoutineItem() {

    override val itemId: Long
        get() = Long.MIN_VALUE

}

data class AmrapRoutineItem(
    override val routineOrdinal: Int,
    override val routine: Routine,
    override val isDeloadRoutine: Boolean,
    val repetitions: Int
): RoutineItem(routineOrdinal, routine, isDeloadRoutine) {

    override val itemId: Long
        get() = -1L

}

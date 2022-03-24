package kr.valor.juggernaut.ui.session

import kr.valor.juggernaut.domain.session.model.Routine

sealed class SessionRoutineItem {

    abstract val itemId: Long

}

open class RoutineItem(open val routine: Routine): SessionRoutineItem() {

    override val itemId: Long
        get() = routine.routineId

    override fun equals(other: Any?): Boolean =
        (other as RoutineItem).routine == this.routine

    override fun hashCode(): Int {
        return routine.hashCode()
    }

}

data class FooterItem(val buttonText: String): SessionRoutineItem() {

    override val itemId: Long
        get() = Long.MIN_VALUE

}

data class AmrapRoutineItem(override val routine: Routine, val repetitions: Int): RoutineItem(routine) {

    override val itemId: Long
        get() = Long.MAX_VALUE

}

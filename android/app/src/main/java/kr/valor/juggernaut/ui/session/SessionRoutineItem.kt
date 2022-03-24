package kr.valor.juggernaut.ui.session

import kr.valor.juggernaut.domain.session.model.Routine

sealed class SessionRoutineItem {

    abstract val itemId: Long

}

open class RoutineItem(open val routine: Routine): SessionRoutineItem() {

    override val itemId: Long
        get() = routine.hashCode().toLong()

}

data class FooterItem(val buttonText: String): SessionRoutineItem() {

    override val itemId: Long
        get() = Long.MIN_VALUE

}

data class AmrapRoutineItem(override val routine: Routine, val repetitions: Int): RoutineItem(routine)

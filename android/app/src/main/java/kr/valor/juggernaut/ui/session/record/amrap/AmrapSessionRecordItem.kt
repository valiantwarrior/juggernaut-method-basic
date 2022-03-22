package kr.valor.juggernaut.ui.session.record.amrap

import kr.valor.juggernaut.domain.session.model.Routine

sealed class AmrapSessionRecordItem {

    abstract val itemId: Long

    data class WarmupRoutineItem(val routine: Routine): AmrapSessionRecordItem() {
        override val itemId: Long
            get() = routine.hashCode().toLong()
    }

    data class AmrapRoutineItem(val routine: Routine): AmrapSessionRecordItem() {
        override val itemId: Long
            get() = routine.hashCode().toLong()
    }

    object Footer: AmrapSessionRecordItem() {
        override val itemId: Long
            get() = Long.MIN_VALUE
    }

}

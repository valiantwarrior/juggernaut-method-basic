package kr.valor.juggernaut.ui.session

import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.Session

val List<SessionRoutineItem>.withFooterItem: List<SessionRoutineItem>
    get() = this + FooterItem

fun List<Routine>.toSessionRoutineItems(): List<SessionRoutineItem> =
    map { routine -> RoutineItem(routine = routine) }

fun Session.getSessionRoutineItems(addFooterItem: Boolean = true): List<SessionRoutineItem> {
    val sessionRoutineItems = routines.toSessionRoutineItems()

    return if (addFooterItem) sessionRoutineItems.withFooterItem else sessionRoutineItems
}
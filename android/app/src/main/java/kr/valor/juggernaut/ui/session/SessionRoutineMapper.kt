package kr.valor.juggernaut.ui.session

import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.Session

fun List<SessionRoutineItem>.withFooterItem(footerButtonText: String): List<SessionRoutineItem> =
    this + FooterItem(footerButtonText)


fun List<Routine>.toSessionRoutineItems(): List<SessionRoutineItem> =
    map { routine -> RoutineItem(routine = routine) }

fun Session.getSessionRoutineItems(addFooterItem: Boolean = true, footerButtonText: String): List<SessionRoutineItem> {
    val sessionRoutineItems = routines.toSessionRoutineItems()

    return if (addFooterItem) sessionRoutineItems.withFooterItem(footerButtonText) else sessionRoutineItems
}
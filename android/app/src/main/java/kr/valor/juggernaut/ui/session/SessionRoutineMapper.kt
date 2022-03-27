package kr.valor.juggernaut.ui.session

import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.Session

fun List<SessionRoutineItem>.withFooterItem(footerButtonText: String): List<SessionRoutineItem> =
    this + FooterItem(footerButtonText)


fun List<Routine>.toSessionRoutineItems(isDeloadRoutine: Boolean): List<SessionRoutineItem> =
    mapIndexed { routineOrdinal, routine -> RoutineItem(routineOrdinal, routine, isDeloadRoutine) }

fun Session.getSessionRoutineItems(addFooterItem: Boolean = true, footerButtonText: String, isDeloadRoutine: Boolean): List<SessionRoutineItem> {
    val sessionRoutineItems = routines.toSessionRoutineItems(isDeloadRoutine)

    return if (addFooterItem) sessionRoutineItems.withFooterItem(footerButtonText) else sessionRoutineItems
}
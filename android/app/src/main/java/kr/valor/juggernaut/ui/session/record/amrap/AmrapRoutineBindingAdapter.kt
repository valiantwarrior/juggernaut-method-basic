package kr.valor.juggernaut.ui.session.record.amrap

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.ui.session.AmrapRoutineItem
import kr.valor.juggernaut.ui.session.FooterItem
import kr.valor.juggernaut.ui.session.RoutineItem
import kr.valor.juggernaut.ui.session.record.RecordUiState

@BindingAdapter("recordItems", "repetitions")
fun RecyclerView.bindRecordItem(uiState: RecordUiState, repetitions: Int) {
    bindUiStateWhenAmrapSession(uiState) { amrapSession ->
        val adapter = adapter as AmrapRoutineAdapter
        val session = amrapSession.currentSession
        val warmupRoutineItems = session.warmupRoutines!!.map { routine ->
            RoutineItem(routine = routine)
        }
        val amrapRoutineItem = AmrapRoutineItem(routine = session.amrapRoutine!!, repetitions)
        val routineItems = warmupRoutineItems + amrapRoutineItem + FooterItem

        adapter.submitList(routineItems)
    }
}

private inline fun bindUiStateWhenAmrapSession(uiState: RecordUiState, block: (RecordUiState.AmrapSession) -> Unit) {
    if (uiState !is RecordUiState.AmrapSession) {
        return
    } else {
        block(uiState)
    }
}
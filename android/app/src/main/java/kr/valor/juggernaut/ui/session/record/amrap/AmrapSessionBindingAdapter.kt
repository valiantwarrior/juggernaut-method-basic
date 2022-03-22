package kr.valor.juggernaut.ui.session.record.amrap

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.ui.session.record.RecordUiState

@BindingAdapter("recordItems")
fun RecyclerView.bindRecordItem(uiState: RecordUiState) {
    bindUiStateWhenAmrapSession(uiState) { amrapSession ->
        val adapter = adapter as AmrapSessionAdapter
        val session = amrapSession.currentSession
        val warmupRoutineItems = session.warmupRoutines!!.map { routine ->
            AmrapSessionRecordItem.WarmupRoutineItem(routine = routine)
        }
        val amrapRoutineItem = AmrapSessionRecordItem.AmrapRoutineItem(session.amrapRoutine!!)
        val routineItems = warmupRoutineItems + amrapRoutineItem + AmrapSessionRecordItem.Footer

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
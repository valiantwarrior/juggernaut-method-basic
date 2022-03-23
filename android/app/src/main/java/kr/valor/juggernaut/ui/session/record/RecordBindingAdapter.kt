package kr.valor.juggernaut.ui.session.record

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.ui.session.*

@BindingAdapter("recordRoutineItems", "recordRepetitions")
fun RecyclerView.bindRecordState(uiState: RecordUiState, repetitions: Int?) {
    val (routineItems, adapter) = when(uiState) {
        is RecordUiState.Loading -> return
        is RecordUiState.AmrapSession -> {
            val session = uiState.currentSession
            val warmupRoutineItems = session.warmupRoutines!!.toSessionRoutineItems()
            val amrapRoutineItem = AmrapRoutineItem(session.amrapRoutine!!, repetitions!!)

            (warmupRoutineItems + amrapRoutineItem).withFooterItem to adapter as AmrapRoutineAdapter
        }
        is RecordUiState.DeloadSession -> {
            uiState.currentSession.getSessionRoutineItems() to adapter as DeloadRoutineAdapter
        }
    }

    adapter.submitList(routineItems)
}
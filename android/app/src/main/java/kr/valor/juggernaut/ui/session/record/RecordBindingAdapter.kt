package kr.valor.juggernaut.ui.session.record

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.session.*

@BindingAdapter("recordRoutineItems", "recordRepetitions")
fun RecyclerView.bindRecordState(uiState: RecordUiState, repetitions: Int?) {
    val recordFooterButtonText = context.getString(R.string.session_footer_button_text_record)
    val (routineItems, adapter) = when(uiState) {
        is RecordUiState.Loading -> return
        is RecordUiState.AmrapSession -> {
            val session = uiState.currentSession
            val warmupRoutineItems = session.warmupRoutines!!.toSessionRoutineItems()
            val amrapRoutineItem = AmrapRoutineItem(session.amrapRoutine!!, repetitions!!)

            (warmupRoutineItems + amrapRoutineItem).withFooterItem(recordFooterButtonText) to adapter as AmrapRoutineAdapter
        }
        is RecordUiState.DeloadSession -> {
            uiState.currentSession.getSessionRoutineItems(footerButtonText = recordFooterButtonText) to adapter as DeloadRoutineAdapter
        }
    }

    adapter.submitList(routineItems)
}
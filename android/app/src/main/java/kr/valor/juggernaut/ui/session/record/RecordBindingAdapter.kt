package kr.valor.juggernaut.ui.session.record

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kr.valor.juggernaut.R
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.session.*
import kr.valor.juggernaut.ui.session.preview.PreviewUiState

@BindingAdapter("recordRoutineItems", "recordRepetitions")
fun RecyclerView.bindRecordState(uiState: RecordUiState, repetitions: Int?) {
    val recordFooterButtonText = context.getString(R.string.session_footer_button_text_record)
    val (routineItems, adapter) = when(uiState) {
        is RecordUiState.Loading -> return
        is RecordUiState.AmrapSession -> {
            val session = uiState.currentSession
            val warmupRoutineItems = session.warmupRoutines!!.toSessionRoutineItems(false)
            val amrapRoutineItem = AmrapRoutineItem(warmupRoutineItems.size, session.amrapRoutine!!, false, repetitions!!)

            (warmupRoutineItems + amrapRoutineItem).withFooterItem(recordFooterButtonText) to adapter as AmrapRoutineAdapter
        }
        is RecordUiState.DeloadSession -> {
            uiState.currentSession.getSessionRoutineItems(footerButtonText = recordFooterButtonText, isDeloadRoutine = true) to adapter as DeloadRoutineAdapter
        }
    }

    adapter.submitList(routineItems)
}

@BindingAdapter("amrapRoutineBaseInformation")
fun TextView.bindAmrapRoutineBaseInformation(amrapRoutineItem: AmrapRoutineItem?) {
    amrapRoutineItem ?: return

    val amrapRoutine = amrapRoutineItem.routine
    @StringRes val amrapRoutineTextFormat =
        R.string.session_routine_item_amrap_routine_info_text_format

    val routineWeights = amrapRoutine.weights
    val routineRepetitions = amrapRoutine.baseIntensity.repetitions
    val routineIntensityPercentageString = amrapRoutine.baseIntensity.approximationIntensityPercentageValue.toString()

    text = resources.getString(amrapRoutineTextFormat, routineWeights, routineRepetitions, routineIntensityPercentageString, "kg")
}

@BindingAdapter("amrapRoutineBaseRepetitionIndicator")
fun TextView.bindAmrapRoutineBaseInformationPercentage(amrapRoutineItem: AmrapRoutineItem?) {
    amrapRoutineItem ?: return

    val amrapRoutine = amrapRoutineItem.routine
    @StringRes val amrapRoutineTextFormat =
        R.string.session_routine_item_amrap_routine_rep_indicator_text_format

    text = resources.getString(amrapRoutineTextFormat, amrapRoutine.baseIntensity.repetitions)
}

@BindingAdapter("recordAppBarPhaseText")
fun TextView.bindRecordAppBarPhaseText(uiState: RecordUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.progression.phase.name
    }
}

@BindingAdapter("recordAppBarMicrocycleText")
fun TextView.bindRecordAppBarMicrocycleText(uiState: RecordUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.progression.microCycle.name
    }
}

@BindingAdapter("recordAppBarLiftCategoryNameText")
fun TextView.bindRecordAppBarLiftCategoryNameText(uiState: RecordUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.category.name
    }
}

@BindingAdapter("recordAppBarLiftCategoryIcon")
fun ImageView.bindRecordAppBarLiftCategoryIcon(uiState: RecordUiState) {
    bindUiStateSession(uiState) { session ->
        @DrawableRes val iconResId = getLiftCategoryIcon(session.category)

        setImageResource(iconResId)
    }
}


private inline fun bindUiStateSession(uiState: RecordUiState, block: (Session) -> Unit) {
    val session = when(uiState) {
        is RecordUiState.Loading -> return
        is RecordUiState.AmrapSession -> uiState.currentSession
        is RecordUiState.DeloadSession -> uiState.currentSession
    }
    block(session)
}
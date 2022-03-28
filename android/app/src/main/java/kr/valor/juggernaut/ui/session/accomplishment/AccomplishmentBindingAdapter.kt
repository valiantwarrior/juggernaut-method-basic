package kr.valor.juggernaut.ui.session.accomplishment

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kr.valor.juggernaut.R
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.common.toFormattedString
import kr.valor.juggernaut.ui.session.AmrapRoutineItem
import kr.valor.juggernaut.ui.session.getSessionRoutineItems
import kr.valor.juggernaut.ui.session.record.RecordUiState
import kr.valor.juggernaut.ui.session.toSessionRoutineItems

@BindingAdapter("accomplishmentRoutineItems")
fun RecyclerView.bindAccomplishmentState(uiState: AccomplishmentUiState) {
    if (uiState is AccomplishmentUiState.Loading) {
        return
    }

    val session = (uiState as AccomplishmentUiState.Result).session

    val sessionRoutineItems = when(session.progression.isAmrapSession) {
        false -> {
            session.getSessionRoutineItems(addFooterItem = false, footerButtonText = null, isDeloadRoutine = false)
        }
        true -> {
            val warmupRoutines = session.warmupRoutines!!
            val amrapRoutine = session.amrapRoutine!!

            val warmupRoutineItems = warmupRoutines.toSessionRoutineItems(false)
            val amrapRoutineItem = AmrapRoutineItem(
                routineOrdinal = warmupRoutineItems.size,
                routine = amrapRoutine,
                repetitions = amrapRoutine.reps,
                isDeloadRoutine = false
            )

            warmupRoutineItems + amrapRoutineItem
        }
    }

    (adapter as AccomplishmentAdapter).submitList(sessionRoutineItems)
}

@BindingAdapter("accomplishmentAppBarPhaseText")
fun TextView.bindAccomplishmentAppBarPhaseText(uiState: AccomplishmentUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.progression.phase.name
    }
}

@BindingAdapter("accomplishmentAppBarMicrocycleText")
fun TextView.bindAccomplishmentAppBarMicrocycleText(uiState: AccomplishmentUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.progression.microCycle.name
    }
}

@BindingAdapter("accomplishmentAppBarLiftCategoryNameText")
fun TextView.bindAccomplishmentAppBarLiftCategoryNameText(uiState: AccomplishmentUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.category.name
    }
}

@BindingAdapter("accomplishmentAppBarLiftCategoryIcon")
fun ImageView.bindAccomplishmentAppBarLiftCategoryIcon(uiState: AccomplishmentUiState) {
    bindUiStateSession(uiState) { session ->
        @DrawableRes val iconResId = getLiftCategoryIcon(session.category)

        setImageResource(iconResId)
    }
}

@BindingAdapter("accomplishmentAppBarCompleteDateTime")
fun TextView.bindAccomplishmentAppBarCompleteDateTime(uiState: AccomplishmentUiState) {
    bindUiStateSession(uiState) { session ->
        val completedDateTime = session.completedLocalDateTime!!

        text = completedDateTime.toFormattedString()
    }
}

@BindingAdapter("accomplishmentAppBarTitle")
fun MaterialToolbar.bindAccomplishmentAppBarTitle(uiState: AccomplishmentUiState) {
    bindUiStateSession(uiState) { session ->
        val sessionOrdinal = session.sessionOrdinal!!
        val sessionWeekOrdinal = session.progression.weekOrdinal
        @StringRes val titleFormatId = R.string.session_routine_collapsing_toolbar_title_text_format

        title = resources.getString(titleFormatId, sessionWeekOrdinal, sessionOrdinal, session.category.abbreviatedName)
    }
}


private inline fun bindUiStateSession(uiState: AccomplishmentUiState, block: (Session) -> Unit) {
    val session = when(uiState) {
        is AccomplishmentUiState.Loading -> return
        is AccomplishmentUiState.Result -> uiState.session
    }
    block(session)
}




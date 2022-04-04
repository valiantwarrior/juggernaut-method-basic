package kr.valor.juggernaut.ui.session.accomplishment

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.common.toFormattedString
import kr.valor.juggernaut.ui.session.AmrapRoutineItem
import kr.valor.juggernaut.ui.session.getSessionRoutineItems
import kr.valor.juggernaut.ui.session.toSessionRoutineItems

@BindingAdapter("accomplishmentRoutineItems")
fun RecyclerView.bindAccomplishmentState(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        val session = result.session

        val sessionRoutineItems = when (session.sessionProgression.isAmrapSession) {
            false -> {
                session.getSessionRoutineItems(
                    addFooterItem = false,
                    footerButtonText = null,
                    isDeloadRoutine = false
                )
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
}

@BindingAdapter("accomplishmentAppBarPhaseText")
fun TextView.bindAccomplishmentAppBarPhaseText(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        text = result.session.sessionProgression.phase.name
    }
}

@BindingAdapter("accomplishmentAppBarMicrocycleText")
fun TextView.bindAccomplishmentAppBarMicrocycleText(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        text = result.session.sessionProgression.microCycle.name
    }
}

@BindingAdapter("accomplishmentAppBarLiftCategoryNameText")
fun TextView.bindAccomplishmentAppBarLiftCategoryNameText(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        text = result.session.category.name
    }
}

@BindingAdapter("accomplishmentAppBarLiftCategoryIcon")
fun ImageView.bindAccomplishmentAppBarLiftCategoryIcon(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        @DrawableRes val iconResId = getLiftCategoryIcon(result.session.category)

        setImageResource(iconResId)
    }
}

@BindingAdapter("accomplishmentAppBarCompleteDateTime")
fun TextView.bindAccomplishmentAppBarCompleteDateTime(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        val completedDateTime = result.session.completedLocalDateTime!!

        text = completedDateTime.toFormattedString()
    }
}

@BindingAdapter("accomplishmentAppBarTitle")
fun MaterialToolbar.bindAccomplishmentAppBarTitle(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        val session = result.session
        val sessionOrdinal = session.sessionOrdinal!!
        val sessionWeekOrdinal = session.sessionProgression.weekOrdinal
        @StringRes val titleFormatId = R.string.session_routine_collapsing_toolbar_title_text_format

        title = resources.getString(titleFormatId, sessionWeekOrdinal, sessionOrdinal, session.category.abbreviatedName)
    }
}

@BindingAdapter("accomplishmentExtendedFabIconAndTitle")
fun ExtendedFloatingActionButton.bindAccomplishmentExtendedFabIconAndTitle(uiState: AccomplishmentUiState) {
    bindUiState(uiState) { result ->
        @DrawableRes val extendedFabIconResId = when(result.destinationToken) {
            AccomplishmentDestinationToken.FROM_OVERALL -> R.drawable.ic_bottom_nav_icon_overall_24
            else -> R.drawable.ic_bottom_nav_icon_home_24
        }

        text = resources.getString(R.string.accomplishment_extended_fab_navigate_back)
        setIconResource(extendedFabIconResId)
    }
}

private inline fun bindUiState(uiState: AccomplishmentUiState, block: (AccomplishmentUiState.Result) -> Unit) {
    if (uiState !is AccomplishmentUiState.Result) {
        return
    }
    block(uiState)
}
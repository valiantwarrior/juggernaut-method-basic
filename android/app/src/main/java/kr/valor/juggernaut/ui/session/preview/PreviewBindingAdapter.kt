package kr.valor.juggernaut.ui.session.preview

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kr.valor.juggernaut.R
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.session.*

@BindingAdapter("previewRoutineItems")
fun RecyclerView.bindPreviewState(uiState: PreviewUiState) {
    val previewFooterButtonText = context.getString(R.string.session_footer_button_text_preview)
    when(uiState) {
        is PreviewUiState.Loading -> return
        is PreviewUiState.Result -> {
            val session = uiState.session
            val sessionRoutineItems = when(session.progression.isAmrapSession) {
                false -> {
                    session.getSessionRoutineItems(footerButtonText = previewFooterButtonText, isDeloadRoutine = true)
                }
                true -> {
                    val warmupRoutines = session.warmupRoutines!!
                    val amrapRoutine = session.amrapRoutine!!

                    val warmupRoutineItems =
                        warmupRoutines.toSessionRoutineItems(
                            isDeloadRoutine = false
                        )

                    val amrapRoutineItem = AmrapRoutineItem(
                        routineOrdinal = warmupRoutineItems.size,
                        routine = amrapRoutine,
                        repetitions = amrapRoutine.baseIntensity.repetitions,
                        isDeloadRoutine = false
                    )

                    (warmupRoutineItems + amrapRoutineItem).withFooterItem(previewFooterButtonText)
                }
            }

            (adapter as PreviewAdapter).submitList(sessionRoutineItems)
        }
    }
}

@BindingAdapter("previewRoutineItemOrdinal")
fun TextView.bindPreviewRoutineItemOrdinal(routineItem: RoutineItem?) {
    routineItem ?: return

    @ColorInt val backgroundColorRes = when(routineItem) {
        is AmrapRoutineItem -> resources.getColor(android.R.color.holo_red_light, null)
        else -> resources.getColor(android.R.color.holo_blue_light, null)
    } // TODO("Considering theming")

    setBackgroundColor(backgroundColorRes)
    text = routineItem.routineOrdinal.plus(1).toString()
}

@BindingAdapter("previewRoutineItemTitle")
fun TextView.bindPreviewRoutineItemTitle(routineItem: RoutineItem?) {
    routineItem ?: return

    @StringRes val stringFormatId = when(routineItem) {
        is AmrapRoutineItem -> {
            @ColorInt val iconColor = resources.getColor(android.R.color.holo_red_dark, null)
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_amrap_24, 0, 0, 0)
            TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(iconColor))
            compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.padding_dp_small)
            text = resources.getString(R.string.session_routine_item_amrap_routine_title_text)
            return
        }
        else -> {
            if (routineItem.isDeloadRoutine) {
                R.string.session_routine_item_deload_routine_title_text_format
            } else {
                R.string.session_routine_item_warmup_routine_title_text_format
            }
        }
    }

    text = resources.getString(stringFormatId, routineItem.routineOrdinal + 1)
}

// TODO("Considering lbs unit later")
@BindingAdapter("previewRoutineItemContent")
fun TextView.bindPreviewRoutineItemContent(routineItem: RoutineItem?) {
    routineItem ?: return

    @StringRes val stringFormatId = when(routineItem) {
        is AmrapRoutineItem -> R.string.session_routine_item_amrap_routine_info_text_format
        else -> R.string.session_routine_item_warmup_and_deload_routine_info_text_format
    }

    val routineWeights = routineItem.routine.weights
    val routineRepetitions = routineItem.routine.baseIntensity.repetitions
    val routineIntensityPercentageString = routineItem.routine.baseIntensity.approximationIntensityPercentageValue.toString()

    text = resources.getString(stringFormatId, routineWeights, routineRepetitions, routineIntensityPercentageString, "kg")
}

@BindingAdapter("previewAppBarPhaseText")
fun TextView.bindPreviewAppBarPhaseText(uiState: PreviewUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.progression.phase.name
    }
}

@BindingAdapter("previewAppBarMicrocycleText")
fun TextView.bindPreviewAppBarMicrocycleText(uiState: PreviewUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.progression.microCycle.name
    }
}

@BindingAdapter("previewAppBarLiftCategoryNameText")
fun TextView.bindPreviewAppBarLiftCategoryNameText(uiState: PreviewUiState) {
    bindUiStateSession(uiState) { session ->
        text = session.category.name
    }
}

@BindingAdapter("previewAppBarLiftCategoryIcon")
fun ImageView.bindPreviewAppBarLiftCategoryIcon(uiState: PreviewUiState) {
    bindUiStateSession(uiState) { session ->
        @DrawableRes val iconResId = getLiftCategoryIcon(session.category)

        setImageResource(iconResId)
    }
}

@BindingAdapter("previewAppBarToolbarTitle")
fun MaterialToolbar.bindPreviewAppBarToolbarTitleText(uiState: PreviewUiState) {
    if (uiState !is PreviewUiState.Result) {
        return
    }

    val userProgression = with(uiState.session.progression) {
        UserProgression(methodCycle, phase, microCycle)
    }
    val currentWeek = userProgression.currentMethodMilestone
    val currentDay = uiState.totalCompletedSessionsCount + 1
    @StringRes val titleTextFormat = R.string.session_routine_collapsing_toolbar_title_text_format
    val liftCategoryAbbreviationName = uiState.session.category.abbreviatedName

    title = resources.getString(titleTextFormat, currentWeek, currentDay, liftCategoryAbbreviationName)
}

private inline fun bindUiStateSession(uiState: PreviewUiState, block: (Session) -> Unit) {
    if (uiState !is PreviewUiState.Result) {
        return
    }
    val session = uiState.session
    block(session)
}
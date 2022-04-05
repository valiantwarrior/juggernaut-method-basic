package kr.valor.juggernaut.ui.user

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import kr.valor.juggernaut.R
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.common.toFormattedString

@BindingAdapter("userSessionRecordLiftCategoryIcon")
fun ImageView.bindLiftCategoryIcon(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    @DrawableRes val iconResId = getLiftCategoryIcon(sessionSummary.category)

    setImageResource(iconResId)
}

@BindingAdapter("userSessionRecordPhaseAndMicroCycle")
fun TextView.bindPhaseAndMicrocycle(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    @StringRes val stringFormatId = R.string.session_phase_and_microcycle_text_format
    val (phaseName, microcycleName) = with(sessionSummary.sessionProgression) {
        phase.name to microCycle.name
    }

    text = resources.getString(stringFormatId, phaseName, microcycleName)
}

@BindingAdapter("userSessionRecordDateTime")
fun TextView.bindDateTime(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    val completedDateTime = sessionSummary.completedLocalDateTime!!

    text = completedDateTime.toFormattedString()
}

@BindingAdapter("userSessionAmrapRecordVisibility")
fun ViewGroup.bindVisibility(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    val isVisible = sessionSummary.amrapRoutine != null

    visibility = if (isVisible) View.VISIBLE else View.GONE
}

// TODO("Considering lbs unit later")
@BindingAdapter("userSessionAmrapRecordActual")
fun TextView.bindActualAmrapRecord(sessionSummary: SessionSummary?) {
    sessionSummary ?: return
    val amrapSession = sessionSummary.amrapRoutine ?: return

    @StringRes val stringFormatId = R.string.session_amrap_actual_info_text_format
    val amrapWeights = amrapSession.weights
    val amrapActualRepetitions = amrapSession.reps

    text = resources.getString(stringFormatId, amrapWeights, "kg", amrapActualRepetitions)
}

// TODO("Considering lbs unit later")
@BindingAdapter("userSessionAmrapRecordGoal")
fun TextView.bindAmrapGoal(sessionSummary: SessionSummary?) {
    sessionSummary ?: return
    val amrapSession = sessionSummary.amrapRoutine ?: return

    @StringRes val stringFormatId = R.string.session_amrap_intensity_info_text_format
    val amrapWeights = amrapSession.weights
    val amrapIntensityPercentage = amrapSession.baseIntensity.approximationIntensityPercentageValue.toString()
    val amrapBaseRepetitions = amrapSession.baseIntensity.repetitions

    text = resources.getString(stringFormatId, amrapWeights, "kg", amrapIntensityPercentage, amrapBaseRepetitions)
}
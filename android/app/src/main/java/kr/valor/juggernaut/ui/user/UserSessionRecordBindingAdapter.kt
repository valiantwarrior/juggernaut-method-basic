package kr.valor.juggernaut.ui.user

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import kr.valor.juggernaut.R
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon

@BindingAdapter("userSessionRecordLiftCategoryIcon")
fun ImageView.bindLiftCategoryIcon(session: Session?) {
    session ?: return

    @DrawableRes val iconResId = getLiftCategoryIcon(session.category)

    setImageResource(iconResId)
}

@BindingAdapter("userSessionRecordPhaseAndMicroCycle")
fun TextView.bindPhaseAndMicrocycle(session: Session?) {
    session ?: return

    @StringRes val stringFormatId = R.string.session_phase_and_microcycle_text_format
    val (phaseName, microcycleName) = with(session.progression) {
        phase.name to microCycle.name
    }

    text = resources.getString(stringFormatId, phaseName, microcycleName)
}

@BindingAdapter("userSessionAmrapRecordVisibility")
fun ViewGroup.bindVisibility(session: Session?) {
    session ?: return

    val isVisible = session.amrapRoutine != null

    visibility = if (isVisible) View.VISIBLE else View.GONE
}

// TODO("Considering lbs unit later")
@BindingAdapter("userSessionAmrapRecordActual")
fun TextView.bindActualAmrapRecord(session: Session?) {
    session ?: return
    val amrapSession = session.amrapRoutine ?: return

    @StringRes val stringFormatId = R.string.session_amrap_actual_info_text_format
    val amrapWeights = amrapSession.weights
    val amrapActualRepetitions = amrapSession.reps

    text = resources.getString(stringFormatId, amrapWeights, "kg", amrapActualRepetitions)
}

// TODO("Considering lbs unit later")
@BindingAdapter("userSessionAmrapRecordGoal")
fun TextView.bindAmrapGoal(session: Session?) {
    session ?: return
    val amrapSession = session.amrapRoutine ?: return

    @StringRes val stringFormatId = R.string.session_amrap_intensity_info_text_format
    val amrapWeights = amrapSession.weights
    val amrapIntensityPercentage = amrapSession.baseIntensity.approximationIntensityPercentageValue.toString()
    val amrapBaseRepetitions = amrapSession.baseIntensity.repetitions

    text = resources.getString(stringFormatId, amrapWeights, "kg", amrapIntensityPercentage, amrapBaseRepetitions)
}
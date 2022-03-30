package kr.valor.juggernaut.ui.onboarding.form.page

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition.*

@BindingAdapter("onboardingFormPageBackground")
fun ImageView.setBackgroundImage(pagePosition: FormPagePosition) {
    @DrawableRes val imageResId = when(pagePosition) {
        BENCHPRESS -> R.drawable.ic_lift_category_benchpress
        SQUAT -> R.drawable.ic_lift_category_squat
        OVERHEADPRESS -> R.drawable.ic_lift_category_overheadpress
        DEADLIFT -> R.drawable.ic_lift_category_deadlift
    }

    setImageResource(imageResId)
}

// TODO("Considering lbs unit later")
@BindingAdapter("onboardingFormPageWeights")
fun TextView.setWeightsText(formPageData: FormPageData?) {
    formPageData ?: return

    @StringRes val stringFormatId = R.string.onboarding_weight_input_weights_text_format

    text = resources.getString(stringFormatId, formPageData.inputWeights.toString(), "kg")
}

@BindingAdapter("onboardingFormPageRepetitions")
fun TextView.setRepetitionsText(formPageData: FormPageData?) {
    formPageData ?: return

    text = formPageData.inputRepetitions.toString()
}

@BindingAdapter("onboardingFormPageNextButtonEnabled")
fun Button.setEnable(formPageData: FormPageData?) {
    formPageData ?: return

    isEnabled = when(formPageData.inputRepetitions) {
        0 -> false
        else -> true
    }
}
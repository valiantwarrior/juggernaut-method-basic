package kr.valor.juggernaut.ui.onboarding.confirmation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition.*
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition
import kr.valor.juggernaut.ui.onboarding.form.page.FormPageData

@BindingAdapter("onboardingConfirmLiftCategoryIcon")
fun ImageView.bindLiftCategoryIcon(pagePosition: FormPagePosition?) {
    pagePosition ?: return

    @DrawableRes val imageResId = when(pagePosition) {
        BENCHPRESS -> R.drawable.ic_lift_category_benchpress
        SQUAT -> R.drawable.ic_lift_category_squat
        OVERHEADPRESS -> R.drawable.ic_lift_category_overheadpress
        DEADLIFT -> R.drawable.ic_lift_category_deadlift
    }

    setImageResource(imageResId)
}

@BindingAdapter("onboardingConfirmLiftCategoryIdentityColor")
fun View.bindIdentityColor(pagePosition: FormPagePosition?) {
    pagePosition ?: return

    @ColorInt val liftCategoryIdentityColor = when(pagePosition) {
        BENCHPRESS -> R.color.lift_category_bp_identity_color
        SQUAT -> R.color.lift_category_sq_identity_color
        OVERHEADPRESS -> R.color.lift_category_ohp_identity_color
        DEADLIFT -> R.color.lift_category_dl_identity_color
    }

    setBackgroundResource(liftCategoryIdentityColor)
}

// TODO("Considering lbs unit later")
@BindingAdapter("onboardingConfirmBaseRecord")
fun TextView.bindFormPageRecord(formPageData: FormPageData?) {
    formPageData ?: return

    @StringRes val stringFormatId = R.string.onboarding_confirm_base_record_text_format

    text = resources.getString(stringFormatId, formPageData.inputWeights, formPageData.inputRepetitions, "kg")
}
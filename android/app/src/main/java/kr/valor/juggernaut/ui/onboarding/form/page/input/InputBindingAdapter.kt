package kr.valor.juggernaut.ui.onboarding.form.page.input

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.DialogOnboardingWeightInputBinding
import kotlin.math.roundToInt

private val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

private fun View.addConstraint(destination: View, constraintSet: ConstraintSet, margin: Int? = null) {
    margin?.let {
        constraintSet.connect(id, ConstraintSet.END, destination.id, ConstraintSet.END)
    }
    constraintSet.connect(id, ConstraintSet.START, destination.id, ConstraintSet.START, margin?.dp ?: 0)
    constraintSet.connect(id, ConstraintSet.TOP, destination.id, ConstraintSet.TOP)
    constraintSet.connect(id, ConstraintSet.BOTTOM, destination.id, ConstraintSet.BOTTOM)
}

@BindingAdapter("attachPlates")
fun ConstraintLayout.bindContainerAttachPlates(platesStack: List<Double>) {
    val binding =
        DataBindingUtil.findBinding<DialogOnboardingWeightInputBinding>(this) ?: return
    val platesContainer = binding.plateContainerView.apply {
        removeAllViews()
    }
    val constraintSet = ConstraintSet()

    if (platesStack.isEmpty()) {
        return
    }

    constraintSet.apply set@{
        val platesViewStack = mutableListOf<ImageView>()

        for (stackTop in 0..platesStack.lastIndex) {
            val plateView = ImageView(context).apply {
                id = View.generateViewId()
                setImageResource(PLATE_LOOKUP_TABLE[platesStack[stackTop]]!!.plateDrawableRes)
            }.also { imageView ->
                platesViewStack.add(imageView)
                platesContainer.addView(imageView)
            }

            clone(platesContainer)

            plateView.apply {
                when(stackTop) {
                    0 -> addConstraint(platesContainer, this@set)
                    else -> {
                        val plateThickness = PLATE_LOOKUP_TABLE[platesStack[stackTop - 1]]!!.plateThickness
                        addConstraint(platesViewStack[stackTop - 1], this@set, plateThickness)
                    }
                }
            }
            applyTo(platesContainer)
        }
    }
}

// TODO("Considering lbs unit later")
@BindingAdapter("platesWeights")
fun TextView.bindPlatesWeightsText(platesStack: List<Double>) {
    val totalWeights = (platesStack.sum() * 2) + 20.0
    @StringRes val stringFormatId = R.string.onboarding_weight_input_weights_text_format

    text = resources.getString(stringFormatId, totalWeights.toInt().toString(), "kg")
}
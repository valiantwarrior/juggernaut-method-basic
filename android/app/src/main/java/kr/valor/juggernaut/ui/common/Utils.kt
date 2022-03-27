package kr.valor.juggernaut.ui.common

import androidx.annotation.DrawableRes
import com.google.android.material.appbar.AppBarLayout
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.*
import kotlin.String as String

@DrawableRes
fun getLiftCategoryIcon(liftCategory: LiftCategory): Int =
    when(liftCategory) {
        BENCHPRESS -> R.drawable.ic_lift_category_benchpress
        SQUAT -> R.drawable.ic_lift_category_squat
        OVERHEADPRESS -> R.drawable.ic_lift_category_overheadpress
        DEADLIFT -> R.drawable.ic_lift_category_deadlift
    }


val Double.optimizedPrecisionPercentageString: String
    get() {
        return when(this % 1.0) {
            0.0 -> this.toInt().toString()
            else -> this.toString()
        }
    }
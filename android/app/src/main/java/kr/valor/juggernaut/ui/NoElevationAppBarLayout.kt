package kr.valor.juggernaut.ui

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout

/**
 * AppBarLayout's elevation is now controlled via a StateListAnimator.
 * [Android Developer](https://developer.android.com/reference/com/google/android/material/appbar/AppBarLayout#setTargetElevation(float))
 *
 * @see android.animation.StateListAnimator
 */

class NoElevationAppBarLayout : AppBarLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs)

    init {
        // set AppBarLayout elevation 0 by StateListAnimator (hide bottom shadow of toolbar)
        stateListAnimator = StateListAnimator().apply {
            addState(
                IntArray(0),
                ObjectAnimator.ofFloat(this@NoElevationAppBarLayout, "elevation", 0f)
            )
        }
    }

}
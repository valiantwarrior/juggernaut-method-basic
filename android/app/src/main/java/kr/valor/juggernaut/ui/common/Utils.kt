package kr.valor.juggernaut.ui.common

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.*
import kr.valor.juggernaut.domain.settings.model.Theme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@DrawableRes
fun getLiftCategoryIcon(liftCategory: LiftCategory): Int =
    when(liftCategory) {
        BENCHPRESS -> R.drawable.ic_lift_category_benchpress
        SQUAT -> R.drawable.ic_lift_category_squat
        OVERHEADPRESS -> R.drawable.ic_lift_category_overheadpress
        DEADLIFT -> R.drawable.ic_lift_category_deadlift
    }

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).let { instant ->
        LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

fun LocalDateTime.toFormattedString(): String =
    format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))


fun updateTheme(theme: Theme) = AppCompatDelegate.setDefaultNightMode(
    when(theme) {
        Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        Theme.BATTERY_SAVER -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    }
)
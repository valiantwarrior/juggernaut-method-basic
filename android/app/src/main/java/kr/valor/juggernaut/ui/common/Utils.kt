package kr.valor.juggernaut.ui.common

import androidx.annotation.DrawableRes
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.*
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
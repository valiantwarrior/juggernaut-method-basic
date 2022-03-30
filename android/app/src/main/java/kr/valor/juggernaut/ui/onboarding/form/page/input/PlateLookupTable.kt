package kr.valor.juggernaut.ui.onboarding.form.page.input

import androidx.annotation.DrawableRes
import kr.valor.juggernaut.R

data class PlateLookupTableItem(@DrawableRes val plateDrawableRes: Int, val plateThickness: Int)

const val BARBELL_SLEEVE_LENGTH = 120

val PLATE_LOOKUP_TABLE = mapOf(
    25.0 to PlateLookupTableItem(R.drawable.ic_plate_25_kg, 18),
    20.0 to PlateLookupTableItem(R.drawable.ic_plate_20_kg, 15),
    15.0 to PlateLookupTableItem(R.drawable.ic_plate_15_kg, 12),
    10.0 to PlateLookupTableItem(R.drawable.ic_plate_10_kg, 9),
    5.0 to PlateLookupTableItem(R.drawable.ic_plate_5_kg, 7),
    2.5 to PlateLookupTableItem(R.drawable.ic_plate_2_5_kg, 5),
    2.0 to PlateLookupTableItem(R.drawable.ic_plate_2_kg, 5),
    1.5 to PlateLookupTableItem(R.drawable.ic_plate_1_5_kg, 5),
    1.0 to PlateLookupTableItem(R.drawable.ic_plate_1_kg, 3),
    0.5 to PlateLookupTableItem(R.drawable.ic_plate_0_5_kg, 3)
)
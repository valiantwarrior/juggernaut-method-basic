package kr.valor.juggernaut.domain.settings.usecase

import android.os.Build
import kr.valor.juggernaut.domain.settings.model.Theme
import javax.inject.Inject

class GetAvailableThemesUseCase @Inject constructor() {

    operator fun invoke(): List<Theme> = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            listOf(Theme.LIGHT, Theme.DARK, Theme.SYSTEM)
        }
        else -> {
            listOf(Theme.LIGHT, Theme.DARK, Theme.BATTERY_SAVER)
        }
    }

}
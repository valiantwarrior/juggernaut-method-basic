package kr.valor.juggernaut.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.domain.settings.model.Theme
import kr.valor.juggernaut.ui.common.updateTheme
import kr.valor.juggernaut.ui.onboarding.OnboardingActivity

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: LauncherViewModel by viewModels()

        observeFlowEvent(viewModel.launchAction) { action ->
            when(action) {
                is LaunchAction.Main -> {
                    updateThemeAndStartActivity(action.currentTheme, MainActivity::class.java)
                }
                is LaunchAction.Onboarding -> {
                    updateThemeAndStartActivity(action.currentTheme, OnboardingActivity::class.java)
                }
            }
        }
    }

    private fun updateThemeAndStartActivity(theme: Theme, activityClass: Class<*>) {
        updateTheme(theme)
        startActivity(Intent(this, activityClass))
        finish()
    }

}
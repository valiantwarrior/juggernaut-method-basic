package kr.valor.juggernaut.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
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
                    updateTheme(action.theme)
                    startActivity(
                        Intent(this@LauncherActivity, MainActivity::class.java)
                            .apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }
                    )
                }
                is LaunchAction.Onboarding -> {
                    updateTheme(action.theme)
                    startActivity(
                        Intent(this@LauncherActivity, OnboardingActivity::class.java)
                            .apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }
                    )
                }
                else -> {}
            }
            finish()
        }
    }

}
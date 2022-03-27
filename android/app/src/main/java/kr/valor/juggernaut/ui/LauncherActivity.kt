package kr.valor.juggernaut.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.valor.juggernaut.ui.onboarding.OnboardingActivity

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: LauncherViewModel by viewModels()

        observeFlowEvent(viewModel.navigateEvent) { action ->
            when(action) {
                is LaunchNavigationAction.Loading -> { /* do nothing */}
                is LaunchNavigationAction.NavigateMain -> startActivity(MainActivity::class.java)
                is LaunchNavigationAction.NavigateOnboarding -> startActivity(OnboardingActivity::class.java)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModelStore.clear()
        overridePendingTransition(0, 0)
    }

    private fun startActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        finish()
    }

}
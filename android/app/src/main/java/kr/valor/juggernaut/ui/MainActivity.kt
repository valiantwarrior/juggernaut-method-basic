package kr.valor.juggernaut.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ActivityMainBinding
import kr.valor.juggernaut.ui.common.updateTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.setupBottomNavigationMenu()
        setContentView(binding.root)

        observeFlowEvent(mainViewModel.theme) { theme ->
            updateTheme(theme)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.statistic_dest, R.id.overall_dest)
        )

        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    private fun ActivityMainBinding.setupBottomNavigationMenu() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.preview_dest, R.id.record_dest, R.id.accomplishment_dest -> {
                    bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
        bottomNavigationView.setupWithNavController(navController)
    }

}
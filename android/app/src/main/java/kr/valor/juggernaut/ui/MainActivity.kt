package kr.valor.juggernaut.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(
        setOf(R.id.home_dest, R.id.overall_dest, R.id.statistic_dest)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = setupNavGraphWithConditionalStartDestination()
        binding.setupActionBar(navController)
        binding.setupBottomNavigationMenu(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    private fun setupNavGraphWithConditionalStartDestination(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)

        viewModel.navigationEventLiveData.observe(this) { navigationEvent ->
            val destinationId = when(navigationEvent) {
                is NavigationEvent.NavigateToHome -> R.id.home_dest
                is NavigationEvent.NavigateToEmpty -> R.id.empty_dest
            }
            navGraph.setStartDestination(destinationId)
            navController.graph = navGraph
        }

        return navController
    }

    private fun ActivityMainBinding.setupActionBar(navController: NavController) {
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun ActivityMainBinding.setupBottomNavigationMenu(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.empty_dest -> {
                    toolbar.visibility = View.GONE
                    bottomNavigationView.visibility = View.GONE
                }

                R.id.preview_dest -> {
                    bottomNavigationView.visibility = View.GONE
                }

                R.id.record_dest -> {
                    bottomNavigationView.visibility = View.GONE
                }

                R.id.onboarding_dest -> {
                    toolbar.visibility = View.GONE
                    bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
        bottomNavigationView.setupWithNavController(navController)
    }

}
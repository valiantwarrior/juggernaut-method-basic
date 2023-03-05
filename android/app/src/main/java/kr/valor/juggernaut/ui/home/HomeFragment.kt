package kr.valor.juggernaut.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentHomeBinding
import kr.valor.juggernaut.extensions.LateInitReadyOnlyProperty
import kr.valor.juggernaut.ui.MainActivity
import kr.valor.juggernaut.ui.NavigationFragment
import kr.valor.juggernaut.ui.onboarding.OnboardingActivity

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : NavigationFragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private val FragmentHomeBinding.snackbar by LateInitReadyOnlyProperty {
        Snackbar
            .make(root, R.string.home_snackbar_message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.home_snackbar_action_label) {
                showDialog()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false)
        .also(::binding::set)
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            NavigationUI.setupWithNavController(
                toolbar,
                findNavController(),
                AppBarConfiguration(
                    setOf(
                        R.id.home_dest,
                        R.id.statistic_dest,
                        R.id.overall_dest,
                        R.id.settings_dest
                    )
                )
            )

            initViewPagerWithTabLayout()
            initProgressionStateObserver()
        }
    }

    private fun FragmentHomeBinding.initViewPagerWithTabLayout() {
        val tabLayout = tabs
        val viewPager = viewPager

        viewPager.adapter = HomeViewPagerAdapter(this@HomeFragment)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                HOME_PAGE_INDEX -> "HOME"
                DETAIL_PAGE_INDEX -> "DETAIL"
                else -> null
            }
        }.attach()
    }

    private fun FragmentHomeBinding.initProgressionStateObserver() {
        homeViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState is HomeUiState.Done) {
                snackbar.show()
            } else {
                snackbar.dismiss()
            }
        }
    }

    private fun FragmentHomeBinding.showDialog() {
        val dialogTitleText = resources.getString(R.string.home_dialog_title)
        val dialogMessageText = resources.getString(R.string.home_dialog_message)
        val dialogActionPositiveText =
            resources.getString(R.string.home_dialog_decision_using_previous_record_label)
        val dialogActionNegativeText =
            resources.getString(R.string.home_dialog_decision_start_with_new_label)
        val dialogActionNeutralText = resources.getString(R.string.home_dialog_decision_later)

        MaterialAlertDialogBuilder(root.context)
            .setTitle(dialogTitleText)
            .setMessage(dialogMessageText)
            .setPositiveButton(dialogActionPositiveText) { _, _ ->
                homeViewModel.onClickStartMethodWithPreviousRecord()
            }
            .setNegativeButton(dialogActionNegativeText) { _, _ ->
                with(requireActivity() as MainActivity) {
                    startActivity(
                        Intent(this, OnboardingActivity::class.java)
                    )
                    finish()
                }
            }
            .setOnDismissListener {
                snackbar.show()
            }
            .setNeutralButton(dialogActionNeutralText) { _, _ -> }
            .show()
    }

}


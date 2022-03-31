package kr.valor.juggernaut.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentSettingsBinding
import kr.valor.juggernaut.ui.LauncherActivity
import kr.valor.juggernaut.ui.MainActivity
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = settingsViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = binding.toolbar
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.statistic_dest, R.id.overall_dest, R.id.settings_dest)
        )
        NavigationUI.setupWithNavController(toolbar, findNavController(), appBarConfiguration)

        initEventObserver()
        binding.initHaltButtonClickListener()
    }

    private fun FragmentSettingsBinding.initHaltButtonClickListener() {
        settingsStopMethodButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.settings_halt_method_dialog_title_text)
                .setMessage(R.string.settings_halt_method_dialog_message_text)
                .setPositiveButton(R.string.settings_halt_method_dialog_positive_button_text) { _, _ ->
                    settingsViewModel.onHaltMethodClicked()
                }
                .setNegativeButton(R.string.settings_halt_method_dialog_negative_button_text) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun initEventObserver() {
        observeFlowEvent(settingsViewModel.haltEventFlow) { event ->
            when(event) {
                SettingsHaltEvent.Finish -> {
                    val activity = requireActivity() as MainActivity
                    val intent = Intent(activity, LauncherActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }
            }
        }
    }

}
package kr.valor.juggernaut.ui.settings

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.valor.juggernaut.R
import kr.valor.juggernaut.domain.settings.model.Theme
import kr.valor.juggernaut.extensions.LateInitReadyOnlyProperty

@AndroidEntryPoint
class ThemeSettingDialogFragment : AppCompatDialogFragment() {

    private val viewModel: SettingsViewModel by viewModels()

    private val listAdapter by LateInitReadyOnlyProperty {
        ArrayAdapter<ThemeHolder>(
            requireContext(),
            android.R.layout.simple_list_item_single_choice
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.settings_select_theme_title)
            .setSingleChoiceItems(listAdapter, 0) { dialog, position ->
                listAdapter.getItem(position)?.theme?.let(viewModel::onThemeSelected)
                dialog.dismiss()
            }
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.availableThemes.collect { themes ->
                    listAdapter.clear()
                    listAdapter.addAll(
                        themes.map { theme ->
                            ThemeHolder(theme, getTitleForTheme(theme))
                        }
                    )

                    updateSelectedItem(viewModel.selectedTheme.value)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedTheme.collect { updateSelectedItem(it) }
            }
        }
    }

    private fun updateSelectedItem(selected: Theme?) {
        val selectedPosition = (0 until listAdapter.count).indexOfFirst { index ->
            listAdapter.getItem(index)?.theme == selected
        }
        (dialog as AlertDialog).listView.setItemChecked(selectedPosition, true)
    }

    private fun getTitleForTheme(theme: Theme) = when (theme) {
        Theme.LIGHT -> getString(R.string.settings_select_theme_light)
        Theme.DARK -> getString(R.string.settings_select_theme_dark)
        Theme.SYSTEM -> getString(R.string.settings_select_theme_system)
        Theme.BATTERY_SAVER -> getString(R.string.settings_select_theme_battery_saver)
    }

    companion object {
        fun newInstance() = ThemeSettingDialogFragment()
    }

    private data class ThemeHolder(val theme: Theme, val title: String) {
        override fun toString(): String = title
    }
}

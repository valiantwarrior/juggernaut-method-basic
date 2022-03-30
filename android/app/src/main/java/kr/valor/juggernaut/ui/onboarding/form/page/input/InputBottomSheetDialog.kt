package kr.valor.juggernaut.ui.onboarding.form.page.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.DialogOnboardingWeightInputBinding
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class InputBottomSheetDialog: BottomSheetDialogFragment() {

    private val inputViewModel: InputViewModel by viewModels()

    private lateinit var binding: DialogOnboardingWeightInputBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogOnboardingWeightInputBinding.inflate(inflater, container, false)
            .apply {
                viewModel = inputViewModel
                lifecycleOwner = viewLifecycleOwner
                dismissButton.setOnClickListener {
                    dismiss()
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeFlowEvent(inputViewModel.uiEvent) { event ->
            @StringRes val snackbarMessageResId =  when(event) {
                is InputUiEvent.BarbellFull -> {
                    R.string.onboarding_weight_input_snackbar_message_barbell_full
                }
                is InputUiEvent.BarbellEmpty -> {
                    R.string.onboarding_weight_input_snackbar_message_barbell_empty
                }
            }
            val snackbar = Snackbar.make(view, snackbarMessageResId, Snackbar.LENGTH_SHORT)

            snackbar
                .setAction(R.string.onboarding_weight_input_snackbar_action_label) {
                    snackbar.dismiss()
                }
                .setAnchorView(view)
                .show()
        }
    }

}
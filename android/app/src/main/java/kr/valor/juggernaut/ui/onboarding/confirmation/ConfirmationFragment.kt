package kr.valor.juggernaut.ui.onboarding.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentOnboardingConfirmationBinding
import kr.valor.juggernaut.ui.MainActivity
import kr.valor.juggernaut.ui.observeFlowEvent
import kr.valor.juggernaut.ui.onboarding.OnboardingActivity

@AndroidEntryPoint
class ConfirmationFragment : Fragment() {

    private val confirmationViewModel: ConfirmationViewModel by viewModels()

    private lateinit var binding: FragmentOnboardingConfirmationBinding

    private lateinit var systemBackButtonCallback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        systemBackButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ConfirmationFragmentDirections.actionConfirmationDestToFormDest())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, systemBackButtonCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOnboardingConfirmationBinding.inflate(inflater, container, false)
            .apply {
                viewModel = confirmationViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backButton.setOnClickListener {
            findNavController().navigate(
                ConfirmationFragmentDirections.actionConfirmationDestToFormDest()
            )
        }

        observeFlowEvent(confirmationViewModel.confirmationUiEvent) { event ->
            if (event is ConfirmationUiEvent.Finish) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        systemBackButtonCallback.remove()
    }

}
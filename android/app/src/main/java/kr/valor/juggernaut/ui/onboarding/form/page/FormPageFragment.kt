package kr.valor.juggernaut.ui.onboarding.form.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.FragmentOnboardingFormPageBinding
import kr.valor.juggernaut.ui.onboarding.form.FormFragment
import kr.valor.juggernaut.ui.onboarding.form.FormFragmentDirections
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition

@AndroidEntryPoint
class FormPageFragment: Fragment() {

    private val pageViewModel: FormPageViewModel by viewModels()

    private lateinit var pagePosition: FormPagePosition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pagePositionName = arguments?.getString(PAGE_POSITION_KEY)!!

        pagePosition = FormPagePosition.valueOf(pagePositionName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentOnboardingFormPageBinding.inflate(inflater, container, false)
            .apply {
                viewModel = pageViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        val parentFragment = requireParentFragment() as FormFragment

        binding.nextButton.setOnClickListener {
            parentFragment.onNextPageClicked()
        }

        binding.backButton.setOnClickListener {
            parentFragment.onPreviousClicked()
        }

        binding.weightInputArea.setOnClickListener {
            findNavController().navigate(
                FormFragmentDirections.actionFormDestToFormWeightInputBottomSheetDialog(pagePosition)
            )
        }

        return binding.root
    }

    companion object {

        const val PAGE_POSITION_KEY = "PAGE_POSITION_NAME"

        fun newInstance(pagePosition: FormPagePosition): FormPageFragment {
            val fragment = FormPageFragment()
            val argument = Bundle()
            argument.putString(PAGE_POSITION_KEY, pagePosition.name)
            fragment.arguments = argument

            return fragment
        }
    }
}
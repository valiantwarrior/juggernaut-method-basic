package kr.valor.juggernaut.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentOnboardingBinding
import kr.valor.juggernaut.ui.observeFlowEvent
import kr.valor.juggernaut.ui.onboarding.OnboardingPagerAdapter.Companion.FOOTER_PAGE_POSITION

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
            .apply {
                initViewPager()
                initEventObserver()
            }

        return binding.root
    }

    private fun FragmentOnboardingBinding.initViewPager() {
        val viewPagerAdapter = with(onboardingViewModel) {
            OnboardingPagerAdapter(
                this,
                viewLifecycleOwner,
                submitClickListener = SubmitClickListener { pagePosition ->
                    when(pagePosition) {
                        FOOTER_PAGE_POSITION -> onClickComplete()
                        else -> onClickSubmit(pagePosition)
                    }
                },
                backClickListener = BackClickListener { pagePosition ->
                    onClickBack(pagePosition - 1)
                }
            )
        }

        onboardingPager.isUserInputEnabled = false
        onboardingPager.adapter = viewPagerAdapter
    }

    private fun FragmentOnboardingBinding.initEventObserver() {
        observeFlowEvent(onboardingViewModel.uiEventFlow) { event ->
            when(event) {
                is OnboardingUiEvent.Next -> onboardingPager.currentItem = event.nextPagePosition
                is OnboardingUiEvent.Previous -> onboardingPager.currentItem = event.previousPagePosition
                OnboardingUiEvent.Done -> findNavController().navigate(R.id.action_onboarding_dest_to_home_dest)
            }
        }
    }

}

class SubmitClickListener(private val clickListener: (Int) -> Unit) {
    fun onClick(pagePosition: Int) = clickListener(pagePosition)
}

class BackClickListener(private val clickListener: (Int) -> Unit) {
    fun onClick(pagePosition: Int) = clickListener(pagePosition)
}
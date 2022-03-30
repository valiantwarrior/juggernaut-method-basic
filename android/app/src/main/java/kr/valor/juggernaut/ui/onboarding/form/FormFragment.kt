package kr.valor.juggernaut.ui.onboarding.form

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentOnboardingFormBinding

@AndroidEntryPoint
class FormFragment : Fragment() {

    private lateinit var viewPagerAdapter: FormViewPagerAdapter

    private lateinit var binding: FragmentOnboardingFormBinding

    private lateinit var viewPagerSystemBackButtonCallback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewPagerSystemBackButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.formViewPager.currentItem -= 1
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, viewPagerSystemBackButtonCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewPagerAdapter = FormViewPagerAdapter(this)
        binding = FragmentOnboardingFormBinding.inflate(inflater, container, false)
            .apply {
                formViewPager.adapter = viewPagerAdapter
                formViewPager.isUserInputEnabled = false
            }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        viewPagerSystemBackButtonCallback.remove()
    }

    fun onNextPageClicked() {
        when(binding.formViewPager.currentItem) {
            viewPagerAdapter.itemCount - 1 -> findNavController().navigate(FormFragmentDirections.actionFormDestToConfirmationDest())
            else -> binding.formViewPager.currentItem += 1
        }
    }

    fun onPreviousClicked() {
        when(binding.formViewPager.currentItem) {
            0 -> findNavController().navigate(FormFragmentDirections.actionFormDestToHeaderDest())
            else -> binding.formViewPager.currentItem -= 1
        }
    }
}
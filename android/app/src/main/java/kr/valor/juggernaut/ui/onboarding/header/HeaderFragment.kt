package kr.valor.juggernaut.ui.onboarding.header

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentOnboardingHeaderBinding
import kr.valor.juggernaut.ui.home.HomeFragmentDirections

@AndroidEntryPoint
class HeaderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentOnboardingHeaderBinding.inflate(inflater, container, false)
            .apply {
                startButton.setOnClickListener {
                    findNavController().navigate(
                        HeaderFragmentDirections.actionHeaderDestToFormDest()
                    )
                }
            }

        return binding.root
    }

}
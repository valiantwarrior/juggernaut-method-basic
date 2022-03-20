package kr.valor.juggernaut.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentHomeBinding
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
            .apply {
                viewModel = homeViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            initEventObserver()
        }
    }

    private fun FragmentHomeBinding.initEventObserver() {
        observeFlowEvent(homeViewModel.uiEventFlow) { event ->
            when(event) {
                HomeUiEvent.HaltMethod -> {
                    findNavController().navigate(R.id.action_home_dest_to_empty_dest)
                }
            }
        }
    }

}
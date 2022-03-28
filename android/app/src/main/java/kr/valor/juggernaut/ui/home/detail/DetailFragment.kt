package kr.valor.juggernaut.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.FragmentDetailBinding
import kr.valor.juggernaut.ui.home.HomeFragmentDirections
import kr.valor.juggernaut.ui.home.NavigationClickListener
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
            .apply {
                viewModel = detailViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initAdapter()
        initEventObserver()
    }

    private fun FragmentDetailBinding.initAdapter() {
        sessionsDetailList.adapter = DetailAdapter(
            NavigationClickListener { sessionId ->
                detailViewModel.onClickItem(sessionId)
            }
        )
    }

    private fun initEventObserver() {
        observeFlowEvent(detailViewModel.uiEventFlow) { event ->
            when(event) {
                is DetailUiEvent.NavigateAccomplishment -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeDestToAccomplishmentFragment(event.sessionId)
                    )
                }
            }
        }
    }

}
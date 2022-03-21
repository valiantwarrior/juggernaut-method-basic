package kr.valor.juggernaut.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentHomeBinding
import kr.valor.juggernaut.ui.NavigationFragment
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class HomeFragment : NavigationFragment() {

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
            initAdapter()
        }
    }

    private fun FragmentHomeBinding.initEventObserver() {
        observeFlowEvent(homeViewModel.uiEventFlow) { event ->
            when(event) {
                HomeUiEvent.HaltMethod -> {
                    navigate(HomeFragmentDirections.actionHomeDestToEmptyDest())
                }
                is HomeUiEvent.NavigateSessionPreview -> {
                    navigate(HomeFragmentDirections.actionHomeDestToPreviewDest(event.sessionId))
                }
            }
        }
    }

    private fun FragmentHomeBinding.initAdapter() {
        sessionsSummaryInfoList.adapter = SessionSummaryAdapter(
            navigateClickListener = NavigateClickListener { sessionId ->
                homeViewModel.onClickSessionItem(sessionId)
            }
        )
    }

}

class NavigateClickListener(private val clickListener: (Long) -> Unit) {
    fun onClick(sessionId: Long) = clickListener(sessionId)
}
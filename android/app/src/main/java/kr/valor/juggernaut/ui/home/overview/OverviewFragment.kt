package kr.valor.juggernaut.ui.home.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.FragmentOverviewBinding
import kr.valor.juggernaut.ui.NavigationFragment
import kr.valor.juggernaut.ui.home.HomeFragmentDirections
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class OverviewFragment : NavigationFragment() {

    private val overviewViewModel: OverviewViewModel by viewModels()

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
            .apply {
                viewModel = overviewViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            initEventObserver()
            initRecyclerViewAdapter()
        }
    }

    private fun FragmentOverviewBinding.initEventObserver() {
        observeFlowEvent(overviewViewModel.uiEventFlow) { event ->
            when(event) {
                is OverviewUiEvent.HaltMethod -> {
                    navigate(HomeFragmentDirections.actionHomeDestToEmptyDest())
                }
                is OverviewUiEvent.NavigatePreview -> {
                    navigate(HomeFragmentDirections.actionHomeDestToPreviewDest(event.sessionId))
                }
            }
        }
    }

    private fun FragmentOverviewBinding.initRecyclerViewAdapter() {
        sessionsSummaryInfoList.adapter = SessionSummaryAdapter(
            navigateClickListener = NavigateClickListener { sessionId ->
                overviewViewModel.onClickSessionItem(sessionId)
            }
        )
    }
}

class NavigateClickListener(private val clickListener: (Long) -> Unit) {
    fun onClick(sessionId: Long) = clickListener(sessionId)
}
package kr.valor.juggernaut.ui.home.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kr.valor.juggernaut.databinding.FragmentOverviewBinding
import kr.valor.juggernaut.ui.NavigationFragment
import kr.valor.juggernaut.ui.home.HomeFragmentDirections
import kr.valor.juggernaut.ui.home.NavigationClickListener
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter
import kr.valor.juggernaut.ui.observeFlowEvent

@ExperimentalCoroutinesApi
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
                is OverviewUiEvent.NavigatePreview -> {
                    navigate(HomeFragmentDirections.actionHomeDestToPreviewDest(event.sessionId))
                }
                is OverviewUiEvent.NavigateAccomplishment -> {
                    navigate(HomeFragmentDirections.actionHomeDestToAccomplishmentFragment(event.sessionId))
                }
            }
        }
    }

    private fun FragmentOverviewBinding.initRecyclerViewAdapter() {
        sessionsSummaryInfoList.adapter = SessionSummaryAdapter(
            navigateClickListener = NavigationClickListener { sessionId ->
                overviewViewModel.onClickSessionItem(sessionId)
            }
        )
    }
}
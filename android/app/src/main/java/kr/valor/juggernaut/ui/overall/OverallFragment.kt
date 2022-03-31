package kr.valor.juggernaut.ui.overall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentOverallBinding
import kr.valor.juggernaut.ui.home.NavigationClickListener
import kr.valor.juggernaut.ui.home.detail.DetailAdapter
import kr.valor.juggernaut.ui.observeFlowEvent
import kr.valor.juggernaut.ui.session.accomplishment.AccomplishmentDestinationToken

@AndroidEntryPoint
class OverallFragment : Fragment() {

    private val overallViewModel: OverallViewModel by viewModels()

    private lateinit var binding: FragmentOverallBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOverallBinding.inflate(inflater, container, false)
            .apply {
                viewModel = overallViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = binding.toolbar
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.statistic_dest, R.id.overall_dest, R.id.settings_dest)
        )
        NavigationUI.setupWithNavController(toolbar, findNavController(), appBarConfiguration)

        binding.initAdapter()
        binding.initEventObserver()
    }

    private fun FragmentOverallBinding.initAdapter() {
        userCompletedSessionList.adapter = DetailAdapter(
            NavigationClickListener { sessionId ->
                overallViewModel.onClickItem(sessionId)
            }
        )
    }

    private fun FragmentOverallBinding.initEventObserver() {
        observeFlowEvent(overallViewModel.uiEventFlow) { event ->
            when(event) {
                is OverallUiEvent.NavigateAccomplishment -> {
                    root.visibility = View.GONE
                    findNavController().navigate(
                        OverallFragmentDirections
                            .actionOverallDestToAccomplishmentDest(
                                event.sessionId,
                                backDestination = AccomplishmentDestinationToken.FROM_OVERALL
                            )
                    )
                }
            }
        }
    }
}
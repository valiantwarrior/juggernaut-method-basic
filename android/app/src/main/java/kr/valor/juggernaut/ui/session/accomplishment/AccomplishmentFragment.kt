package kr.valor.juggernaut.ui.session.accomplishment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.FragmentAccomplishmentBinding

/**
 * [NONE] is defaultValue. When token is [NONE], back destination is same as [FROM_HOME]
 *
 * @see [main_navigation.xml]
 */
enum class AccomplishmentDestinationToken {
    FROM_HOME, FROM_OVERALL, NONE
}

@AndroidEntryPoint
class AccomplishmentFragment : Fragment() {

    private val accomplishmentViewModel: AccomplishmentViewModel by viewModels()

    private val navArgs: AccomplishmentFragmentArgs by navArgs()

    private lateinit var binding: FragmentAccomplishmentBinding

    private lateinit var navigateToHomeWhenSystemBackButtonClicked: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigateToHomeWhenSystemBackButtonClicked = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(getNavDirections())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, navigateToHomeWhenSystemBackButtonClicked)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAccomplishmentBinding.inflate(inflater, container, false)
            .apply {
                viewModel = accomplishmentViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initAdapter()
        binding.initExtendedFab()
    }

    override fun onDetach() {
        super.onDetach()
        navigateToHomeWhenSystemBackButtonClicked.remove()
    }

    private fun FragmentAccomplishmentBinding.initAdapter() {
        sessionAccomplishmentList.adapter = AccomplishmentAdapter()
    }

    private fun FragmentAccomplishmentBinding.initExtendedFab() {
        backExtendedFab.setOnClickListener {
            root.visibility = View.GONE
            findNavController().navigate(getNavDirections())
        }

        sessionAccomplishmentList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && backExtendedFab.isExtended) {
                    backExtendedFab.shrink()
                }
                if (dy < 0 && !backExtendedFab.isExtended) {
                    backExtendedFab.extend()
                }
            }
        })
    }

    private fun getNavDirections(): NavDirections =
        when(navArgs.backDestination) {
            AccomplishmentDestinationToken.FROM_OVERALL -> AccomplishmentFragmentDirections.actionAccomplishmentDestToOverallDest()
            else -> AccomplishmentFragmentDirections.actionAccomplishmentDestToHomeDest()
        }
}
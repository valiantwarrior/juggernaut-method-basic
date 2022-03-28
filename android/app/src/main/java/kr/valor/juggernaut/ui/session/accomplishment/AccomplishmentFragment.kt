package kr.valor.juggernaut.ui.session.accomplishment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.FragmentAccomplishmentBinding

@AndroidEntryPoint
class AccomplishmentFragment : Fragment() {

    private val accomplishmentViewModel: AccomplishmentViewModel by viewModels()

    private lateinit var binding: FragmentAccomplishmentBinding

    private lateinit var navigateToHomeWhenSystemBackButtonClicked: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigateToHomeWhenSystemBackButtonClicked = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(
                    AccomplishmentFragmentDirections.actionAccomplishmentDestToHomeDest()
                )
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
        binding.initFabAction()
    }

    override fun onDetach() {
        super.onDetach()
        navigateToHomeWhenSystemBackButtonClicked.remove()
    }

    private fun FragmentAccomplishmentBinding.initAdapter() {
        sessionAccomplishmentList.adapter = AccomplishmentAdapter()
    }

    private fun FragmentAccomplishmentBinding.initFabAction() {
        backExtendedFab.setOnClickListener {
            findNavController().navigate(
                AccomplishmentFragmentDirections.actionAccomplishmentDestToHomeDest()
            )
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

}
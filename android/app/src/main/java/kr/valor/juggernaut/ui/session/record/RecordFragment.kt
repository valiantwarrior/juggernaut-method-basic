package kr.valor.juggernaut.ui.session.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentRecordBinding
import kr.valor.juggernaut.domain.session.model.Session.Progression.Companion.DELOAD_SESSION_INDICATOR
import kr.valor.juggernaut.ui.NavigationFragment
import kr.valor.juggernaut.ui.observeFlowEvent
import kr.valor.juggernaut.ui.session.record.amrap.AmrapSessionAdapter

@AndroidEntryPoint
class RecordFragment : NavigationFragment() {

    private val recordViewModel: RecordViewModel by viewModels()

    private val navArgs: RecordFragmentArgs by navArgs()

    private lateinit var binding: FragmentRecordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
            .apply {
                viewModel = recordViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            initEventObserver()
            when(navArgs.baseAmrapRepetitions) {
                DELOAD_SESSION_INDICATOR -> bindDeloadSession()
                else -> bindAmrapSession()
            }
        }
    }

    private fun FragmentRecordBinding.bindAmrapSession() {
        val adapter = AmrapSessionAdapter(
            plusRepsAction = { recordViewModel.accept(RecordUiAction.Plus) },
            minusRepsAction = { recordViewModel.accept(RecordUiAction.Minus) },
            submitAction = { recordViewModel.accept(RecordUiAction.Submit) }
        )
        sessionsRecordList.adapter = adapter
        (sessionsRecordList.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    private fun FragmentRecordBinding.bindDeloadSession() {}

    private fun initEventObserver() {
        observeFlowEvent(recordViewModel.eventFlow) { recordEvent ->
            when(recordEvent) {
                RecordEvent.Done -> navigate(R.id.action_record_dest_to_home_dest)
            }
        }
    }

}
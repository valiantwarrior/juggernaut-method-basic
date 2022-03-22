package kr.valor.juggernaut.ui.session.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.FragmentRecordBinding
import kr.valor.juggernaut.domain.session.model.Session.Progression.Companion.DELOAD_SESSION_INDICATOR
import kr.valor.juggernaut.ui.NavigationFragment
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
            when(navArgs.baseAmrapRepetitions) {
                DELOAD_SESSION_INDICATOR -> bindDeloadSession()
                else -> bindAmrapSession()
            }
        }
    }

    private fun FragmentRecordBinding.bindAmrapSession() {
        sessionsRecordList.adapter = AmrapSessionAdapter()
    }

    private fun FragmentRecordBinding.bindDeloadSession() {}

}
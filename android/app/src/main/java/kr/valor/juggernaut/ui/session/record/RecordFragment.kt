package kr.valor.juggernaut.ui.session.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentRecordBinding
import kr.valor.juggernaut.ui.NavigationFragment

@AndroidEntryPoint
class RecordFragment : NavigationFragment() {

    private val recordViewModel: RecordViewModel by viewModels()

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
//            plusAction = RecordActionClickListener {
//                val currentRepetitions = (recordViewModel.uiState.value as UiState.AmrapSession).amrapRepetitions
//                recordViewModel.accept(UiAction.Plus(currentRepetitions))
//            }
//            minusAction = RecordActionClickListener {
//                val currentRepetitions = (recordViewModel.uiState.value as UiState.AmrapSession).amrapRepetitions
//                recordViewModel.accept(UiAction.Minus(currentRepetitions))
//            }
        }
    }

}

class RecordActionClickListener(private val clickListener: () -> Unit) {
    fun onClick() = clickListener()
}

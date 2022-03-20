package kr.valor.juggernaut.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentEmptyBinding

class ProgressionEmptyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentEmptyBinding.inflate(inflater, container, false).also {
            it.startNewMethodButton.setOnClickListener {
                findNavController().navigate(R.id.action_empty_dest_to_onboarding_dest)
            }
        }.root
    }

}
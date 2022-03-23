package kr.valor.juggernaut.ui.overall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R

@AndroidEntryPoint
class OverallFragment : Fragment() {

    private val overallViewModel: OverallViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overall, container, false)
    }

}
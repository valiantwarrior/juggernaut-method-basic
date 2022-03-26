package kr.valor.juggernaut.ui.statistic

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
import kr.valor.juggernaut.databinding.FragmentStatisticBinding

@AndroidEntryPoint
class StatisticFragment : Fragment() {

    private val statisticViewModel: StatisticViewModel by viewModels()

    private lateinit var binding: FragmentStatisticBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = binding.toolbar
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.statistic_dest, R.id.overall_dest)
        )
        toolbar.title = "Statistic"
        NavigationUI.setupWithNavController(toolbar, findNavController(), appBarConfiguration)
    }

}
package kr.valor.juggernaut.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.FragmentHomeBinding
import kr.valor.juggernaut.ui.NavigationFragment

@AndroidEntryPoint
class HomeFragment : NavigationFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = HomeViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        val toolbar = binding.toolbar
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.statistic_dest, R.id.overall_dest)
        )
        NavigationUI.setupWithNavController(toolbar, findNavController(), appBarConfiguration)

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            HOME_PAGE_INDEX -> "HOME"
            DETAIL_PAGE_INDEX -> "DETAIL"
            else -> null
        }
    }
}


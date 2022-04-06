package kr.valor.juggernaut.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kr.valor.juggernaut.ui.home.detail.DetailFragment
import kr.valor.juggernaut.ui.home.overview.OverviewFragment

const val HOME_PAGE_INDEX = 0
const val DETAIL_PAGE_INDEX = 1

@ExperimentalCoroutinesApi
class HomeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        HOME_PAGE_INDEX to { OverviewFragment() },
        DETAIL_PAGE_INDEX to { DetailFragment() }
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()

}
package kr.valor.juggernaut.ui.onboarding.form

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition.*
import kr.valor.juggernaut.ui.onboarding.form.page.FormPageFragment

enum class FormPagePosition {
    BENCHPRESS, SQUAT, OVERHEADPRESS, DEADLIFT
}

class FormViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val fragmentCreators: Map<FormPagePosition, (FormPagePosition) -> Fragment> = mapOf(
        BENCHPRESS to { FormPageFragment.newInstance(BENCHPRESS) },
        SQUAT to { FormPageFragment.newInstance(SQUAT) },
        OVERHEADPRESS to { FormPageFragment.newInstance(OVERHEADPRESS) },
        DEADLIFT to { FormPageFragment.newInstance(DEADLIFT) }
    )

    override fun getItemCount(): Int = fragmentCreators.size

    override fun createFragment(position: Int): Fragment =
        FormPagePosition.values()[position].let { pagePosition ->
            fragmentCreators[pagePosition]?.invoke(pagePosition) ?: throw IndexOutOfBoundsException()
        }

}
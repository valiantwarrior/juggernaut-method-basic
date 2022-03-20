package kr.valor.juggernaut.ui.onboarding

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.common.LiftCategory

private typealias OnboardingFormViewHolder = OnboardingViewHolder.OnboardingFormViewHolder
private typealias OnboardingFooterViewHolder = OnboardingViewHolder.OnboardingFooterViewHolder

class OnboardingPagerAdapter(
    private val onboardingViewModel: OnboardingViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val submitClickListener: SubmitClickListener,
    private val backClickListener: BackClickListener
): RecyclerView.Adapter<OnboardingViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            FOOTER_PAGE_POSITION -> VIEW_TYPE_FOOTER
            else -> VIEW_TYPE_FORM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder =
        when(viewType) {
            VIEW_TYPE_FOOTER -> OnboardingFooterViewHolder.create(
                parent,
                onboardingViewModel.uiModel,
                lifecycleOwner,
                submitClickListener,
                backClickListener
            )
            else -> OnboardingFormViewHolder.create(
                parent,
                onboardingViewModel.inputWeightsFieldState,
                onboardingViewModel.inputRepetitionsFieldState,
                submitClickListener,
                backClickListener
            )
        }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int = TOTAL_PAGE_COUNT

    companion object {
        private const val VIEW_TYPE_FOOTER = 0
        private const val VIEW_TYPE_FORM = 1
        private const val FOOTER_PAGE_COUNT = 1
        private const val TOTAL_PAGE_COUNT = LiftCategory.TOTAL_LIFT_CATEGORY_COUNT + FOOTER_PAGE_COUNT
        const val FOOTER_PAGE_POSITION = TOTAL_PAGE_COUNT - 1
    }

}




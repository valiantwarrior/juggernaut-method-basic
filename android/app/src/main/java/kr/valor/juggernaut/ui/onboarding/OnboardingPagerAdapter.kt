package kr.valor.juggernaut.ui.onboarding

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.common.LiftCategory

class OnboardingPagerAdapter(
    private val onboardingViewModel: OnboardingViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val startAction: () -> Unit,
    private val submitClickListener: SubmitClickListener,
    private val backClickListener: BackClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            HEADER_PAGE_POSITION -> VIEW_TYPE_HEADER
            FOOTER_PAGE_POSITION -> VIEW_TYPE_FOOTER
            else -> VIEW_TYPE_FORM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            VIEW_TYPE_HEADER -> OnboardingHeaderViewHolder.create(parent, startAction)
            VIEW_TYPE_FOOTER -> OnboardingFooterViewHolder.create(
                parent,
                onboardingViewModel.userInputModelState,
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == HEADER_PAGE_POSITION) {
            return
        }
        (holder as OnboardingViewHolder).bindViewHolder(position)
    }

    override fun getItemCount(): Int = TOTAL_PAGE_COUNT

    companion object {
        private const val VIEW_TYPE_HEADER = -1
        private const val VIEW_TYPE_FOOTER = 0
        private const val VIEW_TYPE_FORM = 1
        private const val FOOTER_PAGE_COUNT = 1
        private const val HEADER_PAGE_COUNT = 1
        private const val TOTAL_PAGE_COUNT = HEADER_PAGE_COUNT + LiftCategory.TOTAL_LIFT_CATEGORY_COUNT + FOOTER_PAGE_COUNT

        const val FOOTER_PAGE_POSITION = TOTAL_PAGE_COUNT - 1
        const val HEADER_PAGE_POSITION = 0
    }

}




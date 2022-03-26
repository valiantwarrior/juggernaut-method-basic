package kr.valor.juggernaut.ui.home.overview

import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory.*
import kr.valor.juggernaut.common.LiftCategory.Companion.TOTAL_LIFT_CATEGORY_COUNT
import kr.valor.juggernaut.databinding.FragmentOverviewBinding
import kr.valor.juggernaut.databinding.ItemHomeSessionSummaryBinding
import kr.valor.juggernaut.databinding.LayoutUserProgressionCardBinding
import kr.valor.juggernaut.domain.progression.model.UserProgression.Companion.OVERALL_METHOD_MILESTONE
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter

@BindingAdapter("sessionSummaries")
fun RecyclerView.bindSessions(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val sessions = uiModel.sessions
        val adapter = adapter as SessionSummaryAdapter

        adapter.submitList(sessions)
    }
}

// TODO("Renaming, improving features, etc")
@BindingAdapter("sessionSummary")
fun MaterialCardView.bindSessionSummary(session: Session?) {
    if (session == null) return

    val binding =
        DataBindingUtil.findBinding<ItemHomeSessionSummaryBinding>(this) ?: return

    binding.sessionSummaryInfoCardView.isEnabled = !session.isCompleted
}

@BindingAdapter("currentUserProgression")
fun MaterialCardView.bindUserProgression(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val binding =
            DataBindingUtil.findBinding<LayoutUserProgressionCardBinding>(this) ?: return
        val fragmentOverviewBinding =
            DataBindingUtil.findBinding<FragmentOverviewBinding>((binding.root.parent) as View) ?: return
        val res = context.resources

        val (titleText, contentText) = with(fragmentOverviewBinding) {
            when(binding) {
                phaseLayout -> {
                    binding.applyPrimaryDarkColorTheme()
                    val phase = uiModel.userProgression.phase
                    val phaseTitleText =
                        res.getString(R.string.home_user_progression_phase_title_text_format, phase.ordinal + 1)

                    phaseTitleText to uiModel.userProgression.phase.name
                }
                microcycleLayout -> {
                    val microCycle = uiModel.userProgression.microCycle
                    val microCycleTitleText =
                        res.getString(R.string.home_user_progression_microcycle_title_text_format, microCycle.ordinal + 1)

                    microCycleTitleText to uiModel.userProgression.microCycle.name
                }
                weeklyAchievementLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.home_weekly_progress_title_text,
                        uiModel.sessions.filter { it.isCompleted }.size,
                        TOTAL_LIFT_CATEGORY_COUNT
                    )
                }
                totalMilestoneLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.home_total_progress_title_text,
                        uiModel.userProgression.currentMethodMilestone,
                        OVERALL_METHOD_MILESTONE
                    )
                }
                detailedMilestoneLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    val overallMethodMilestoneDetailed = OVERALL_METHOD_MILESTONE * TOTAL_LIFT_CATEGORY_COUNT
                    val methodMilestoneDetailed = uiModel.userProgression.currentMethodMilestone.let { baseMilestone ->
                        uiModel.sessions.filter { it.isCompleted }.size * baseMilestone
                    }

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.home_total_progress_detailed_title_text,
                        methodMilestoneDetailed,
                        overallMethodMilestoneDetailed
                    )
                }
                else -> throw IllegalArgumentException("Unknown ViewDataBinding $binding")
            }
        }

        with(binding) {
            progressionTitleText.text = titleText
            progressionContentText.text = contentText
        }
    }
}

private fun FragmentOverviewBinding.getOverviewMileStoneContentAndTitleTextPair(
    @StringRes milestoneTitleId: Int,
    milestoneParam1: Int,
    milestoneParam2: Int
): Pair<String, String> {
    val milestoneContent = root.resources.getString(R.string.home_milestone_text_format, milestoneParam1, milestoneParam2)
    val milestoneTitle = root.resources.getString(milestoneTitleId)

    return milestoneTitle to milestoneContent
}

private fun LayoutUserProgressionCardBinding.applyPrimaryDarkColorTheme() {
    val cardBackgroundColor = ResourcesCompat.getColor(root.resources, R.color.color_primary, null)
    val contentTextColor = ResourcesCompat.getColor(root.resources, R.color.color_on_primary_variant, null)
    val titleTextColor = ResourcesCompat.getColor(root.resources, R.color.white, null)
    (this.root as MaterialCardView).setCardBackgroundColor(cardBackgroundColor)

    progressionContentText.setTextColor(contentTextColor)
    progressionTitleText.setTextColor(titleTextColor)
}

private inline fun bindUiResult(uiResult: UiResult, block: (UiResult.Success) -> Unit) {
    if (uiResult !is UiResult.Success) {
        return
    } else {
        block(uiResult)
    }
}
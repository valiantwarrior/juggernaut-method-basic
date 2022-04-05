package kr.valor.juggernaut.ui.home.overview

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory.Companion.TOTAL_LIFT_CATEGORY_COUNT
import kr.valor.juggernaut.databinding.FragmentOverviewBinding
import kr.valor.juggernaut.databinding.LayoutUserProgressionCardBinding
import kr.valor.juggernaut.domain.progression.model.UserProgression.Companion.OVERALL_METHOD_MILESTONE
import kr.valor.juggernaut.domain.progression.model.UserProgression.Companion.SESSION_COUNT_PER_PHASE
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter
import com.google.android.material.R as Material

@BindingAdapter("sessionSummaries")
fun RecyclerView.bindSessions(uiState: OverviewUiState) {
    bindUiState(uiState) { result ->
        val sessionSummaries = result.sessionSummaries
        val adapter = adapter as SessionSummaryAdapter

        adapter.submitList(sessionSummaries)
    }
}

@BindingAdapter("overviewSessionSummaryLiftCategoryIcon")
fun ImageView.bindLiftCategoryIcon(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    @DrawableRes val iconResId = getLiftCategoryIcon(sessionSummary.category)

    setImageResource(iconResId)
}

@BindingAdapter("overviewSessionSummaryLiftCategoryName")
fun TextView.bindLiftCategoryName(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    text = sessionSummary.category.name
}

@BindingAdapter("overviewSessionSummaryAmrapInfo")
fun TextView.bindAmrapInfo(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    sessionSummary.amrapRoutine?.let { amrapRoutine ->
        @StringRes val stringFormatId = R.string.overview_session_brief_amrap_info_text_format
        val amrapWeights = amrapRoutine.weights
        val amrapBaseRepetitions = amrapRoutine.baseIntensity.repetitions

        text = resources.getString(stringFormatId, amrapWeights, "kg", amrapBaseRepetitions)
        visibility = View.VISIBLE
    } ?: run {
        visibility = View.GONE
    }
}

@BindingAdapter("overviewSessionSummaryAmrapIntensityInfo")
fun TextView.bindAmrapIntensityInfo(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    sessionSummary.amrapRoutine?.let { amrapRoutine ->
        @StringRes val stringFormatId = R.string.overview_session_brief_amrap_intensity_info_text_format
        val amrapIntensityPercentage = amrapRoutine.baseIntensity.approximationIntensityPercentageValue.toString()
        val amrapBaseRepetitions = amrapRoutine.baseIntensity.repetitions

        text = resources.getString(stringFormatId, amrapIntensityPercentage ,amrapBaseRepetitions)
        visibility = View.VISIBLE
    } ?: run {
        visibility = View.GONE
    }
}

@BindingAdapter("overviewSessionSummaryAction")
fun TextView.bindStatus(sessionSummary: SessionSummary?) {
    sessionSummary ?: return

    @StringRes val actionStringId = sessionSummary.completedLocalDateTime?.let {
        R.string.overview_session_action_done
    } ?: R.string.overview_session_action_start

    text = resources.getString(actionStringId)
}

@BindingAdapter("currentUserProgression")
fun MaterialCardView.bindUserProgression(uiState: OverviewUiState) {
    bindUiState(uiState) { result ->
        val binding =
            DataBindingUtil.findBinding<LayoutUserProgressionCardBinding>(this) ?: return
        val containerBinding =
            DataBindingUtil.findBinding<FragmentOverviewBinding>((binding.root.parent) as View) ?: return
        val res = context.resources

        val (titleText, contentText) = with(containerBinding) {
            when(binding) {
                phaseLayout -> {
                    binding.applyPrimaryDarkColorTheme()
                    val phase = result.userProgression.phase
                    val phaseTitleText =
                        res.getString(R.string.overview_user_progression_phase_title_text_format, phase.ordinal + 1)

                    phaseTitleText to result.userProgression.phase.name
                }
                microcycleLayout -> {
                    val microCycle = result.userProgression.microCycle
                    val microCycleTitleText =
                        res.getString(R.string.overview_user_progression_microcycle_title_text_format, microCycle.ordinal + 1)

                    microCycleTitleText to result.userProgression.microCycle.name
                }
                weeklyAchievementLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.overview_weekly_progress_title_text,
                        result.sessionSummaries.filter { it.isCompletedSession }.size,
                        TOTAL_LIFT_CATEGORY_COUNT
                    )
                }
                totalMilestoneLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.overview_total_progress_title_text,
                        result.userProgression.currentMethodMilestone,
                        OVERALL_METHOD_MILESTONE
                    )
                }
                detailedMilestoneLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    val overallMethodMilestoneDetailed = OVERALL_METHOD_MILESTONE * TOTAL_LIFT_CATEGORY_COUNT
                    val methodMilestoneDetailed =
                        (result.userProgression.phase.ordinal * SESSION_COUNT_PER_PHASE) +
                                (result.userProgression.microCycle.ordinal * TOTAL_LIFT_CATEGORY_COUNT) +
                                result.sessionSummaries.filter { it.isCompletedSession }.size

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.overview_total_progress_detailed_title_text,
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
    val milestoneContent = root.resources.getString(R.string.overview_milestone_text_format, milestoneParam1, milestoneParam2)
    val milestoneTitle = root.resources.getString(milestoneTitleId)

    return milestoneTitle to milestoneContent
}

private fun LayoutUserProgressionCardBinding.applyPrimaryDarkColorTheme() {
    val (@ColorInt textColor, @ColorInt cardBackgroundColor) = with(root.context.theme) {
        resolveMaterialColorAttribute(Material.attr.colorOnPrimary) to
                resolveMaterialColorAttribute(Material.attr.colorPrimary)
    }

    (this.root as MaterialCardView).setCardBackgroundColor(cardBackgroundColor)
    progressionContentText.setTextColor(textColor)
    progressionTitleText.setTextColor(textColor)
}

@ColorInt private fun Resources.Theme.resolveMaterialColorAttribute(resId: Int): Int =
    TypedValue().also { resolveAttribute(resId, it, true) }.data

private inline fun bindUiState(uiState: OverviewUiState, block: (OverviewUiState.Result) -> Unit) {
    if (uiState !is OverviewUiState.Result) {
        return
    } else {
        block(uiState)
    }
}
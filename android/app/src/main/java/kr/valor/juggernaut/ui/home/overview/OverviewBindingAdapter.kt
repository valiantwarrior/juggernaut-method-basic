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
import com.google.android.material.R as Material
import kr.valor.juggernaut.R as R
import kr.valor.juggernaut.common.LiftCategory.Companion.TOTAL_LIFT_CATEGORY_COUNT
import kr.valor.juggernaut.databinding.FragmentOverviewBinding
import kr.valor.juggernaut.databinding.LayoutUserProgressionCardBinding
import kr.valor.juggernaut.domain.progression.model.UserProgression.Companion.OVERALL_METHOD_MILESTONE
import kr.valor.juggernaut.domain.progression.model.UserProgression.Companion.SESSION_COUNT_PER_PHASE
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter

@BindingAdapter("sessionSummaries")
fun RecyclerView.bindSessions(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val sessions = uiModel.sessions
        val adapter = adapter as SessionSummaryAdapter

        adapter.submitList(sessions)
    }
}

@BindingAdapter("overviewSessionSummaryLiftCategoryIcon")
fun ImageView.bindLiftCategoryIcon(session: Session?) {
    session ?: return

    @DrawableRes val iconResId = getLiftCategoryIcon(session.category)

    setImageResource(iconResId)
}

@BindingAdapter("overviewSessionSummaryLiftCategoryName")
fun TextView.bindLiftCategoryName(session: Session?) {
    session ?: return

    text = session.category.name
}

@BindingAdapter("overviewSessionSummaryAmrapInfo")
fun TextView.bindAmrapInfo(session: Session?) {
    session ?: return

    session.amrapRoutine?.let { amrapRoutine ->
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
fun TextView.bindAmrapIntensityInfo(session: Session?) {
    session ?: return

    session.amrapRoutine?.let { amrapRoutine ->
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
fun TextView.bindStatus(session: Session?) {
    session ?: return

    @StringRes val actionStringId = when(session.isCompleted) {
        true -> R.string.overview_session_action_done
        else -> R.string.overview_session_action_start
    }

    text = resources.getString(actionStringId)
}

@BindingAdapter("currentUserProgression")
fun MaterialCardView.bindUserProgression(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val binding =
            DataBindingUtil.findBinding<LayoutUserProgressionCardBinding>(this) ?: return
        val containerBinding =
            DataBindingUtil.findBinding<FragmentOverviewBinding>((binding.root.parent) as View) ?: return
        val res = context.resources

        val (titleText, contentText) = with(containerBinding) {
            when(binding) {
                phaseLayout -> {
                    binding.applyPrimaryDarkColorTheme()
                    val phase = uiModel.userProgression.phase
                    val phaseTitleText =
                        res.getString(R.string.overview_user_progression_phase_title_text_format, phase.ordinal + 1)

                    phaseTitleText to uiModel.userProgression.phase.name
                }
                microcycleLayout -> {
                    val microCycle = uiModel.userProgression.microCycle
                    val microCycleTitleText =
                        res.getString(R.string.overview_user_progression_microcycle_title_text_format, microCycle.ordinal + 1)

                    microCycleTitleText to uiModel.userProgression.microCycle.name
                }
                weeklyAchievementLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.overview_weekly_progress_title_text,
                        uiModel.sessions.filter { it.isCompleted }.size,
                        TOTAL_LIFT_CATEGORY_COUNT
                    )
                }
                totalMilestoneLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    getOverviewMileStoneContentAndTitleTextPair(
                        R.string.overview_total_progress_title_text,
                        uiModel.userProgression.currentMethodMilestone,
                        OVERALL_METHOD_MILESTONE
                    )
                }
                detailedMilestoneLayout -> {
                    binding.applyPrimaryDarkColorTheme()

                    val overallMethodMilestoneDetailed = OVERALL_METHOD_MILESTONE * TOTAL_LIFT_CATEGORY_COUNT
                    val methodMilestoneDetailed =
                        (uiModel.userProgression.phase.ordinal * SESSION_COUNT_PER_PHASE) +
                                (uiModel.userProgression.microCycle.ordinal * TOTAL_LIFT_CATEGORY_COUNT) +
                                (uiModel.sessions.filter { it.isCompleted }.size)

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

private inline fun bindUiResult(uiResult: UiResult, block: (UiResult.Success) -> Unit) {
    if (uiResult !is UiResult.Success) {
        return
    } else {
        block(uiResult)
    }
}
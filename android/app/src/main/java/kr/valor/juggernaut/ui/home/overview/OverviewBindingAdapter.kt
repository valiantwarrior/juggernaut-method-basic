package kr.valor.juggernaut.ui.home.overview

import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.*
import kr.valor.juggernaut.databinding.FragmentHomeBinding
import kr.valor.juggernaut.databinding.FragmentOverviewBinding
import kr.valor.juggernaut.databinding.ItemHomeSessionSummaryBinding
import kr.valor.juggernaut.databinding.LayoutUserProgressionCardBinding
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

    val imageResource = when(session.category) {
        BENCHPRESS -> R.drawable.bg_img_bp
        SQUAT -> R.drawable.bg_img_sq_max
        OVERHEADPRESS -> R.drawable.bg_img_ohp_dmitry
        DEADLIFT -> R.drawable.bg_img_dl_clarence
    }

//    binding.thumbnailImg.setImageResource(imageResource)
//    binding.liftCategoryText.text = session.category.name
    binding.sessionSummaryInfoCardView.isEnabled = !session.isCompleted
}

@BindingAdapter("currentUserProgression")
fun MaterialCardView.bindUserProgression(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val binding =
            DataBindingUtil.findBinding<LayoutUserProgressionCardBinding>(this) ?: return
        val fragmentHomeBinding =
            DataBindingUtil.findBinding<FragmentOverviewBinding>((binding.root.parent) as View) ?: return

        val (titleText, contentText) = with(fragmentHomeBinding) {
            when(binding) {
                phaseLayout -> {
                    binding.applyPhaseLayoutTheme()
                    val phase = uiModel.userProgression.phase
                    val phaseTitleText = root.resources.getString(R.string.home_user_progression_phase_title_text_format, phase.ordinal + 1)
                    phaseTitleText to uiModel.userProgression.phase.name
                }
                microcycleLayout -> {
                    val microCycle = uiModel.userProgression.microCycle
                    val microCycleTitleText = root.resources.getString(R.string.home_user_progression_microcycle_title_text_format, microCycle.ordinal + 1)
                    microCycleTitleText to uiModel.userProgression.microCycle.name
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

private fun LayoutUserProgressionCardBinding.applyPhaseLayoutTheme() {
    val cardBackgroundColor = ResourcesCompat.getColor(root.resources, R.color.color_primary, null)
    val contentTextColor = ResourcesCompat.getColor(root.resources, R.color.color_on_primary_variant, null)
    val titleTextColor = ResourcesCompat.getColor(root.resources, R.color.white, null)
    (this.root as MaterialCardView).setCardBackgroundColor(cardBackgroundColor)

    progressionContentText.setTextColor(contentTextColor)
    progressionTitleText.setTextColor(titleTextColor)
}


@BindingAdapter("currentMicroCycle")
fun TextView.bindMicroCycle(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val microCycle = uiModel.userProgression.microCycle.name

        text = microCycle
    }
}

//@BindingAdapter("milestone")
//fun TextView.bindCurrentMilestone(uiResult: UiResult) {
//    bindUiResult(uiResult) { uiModel ->
//        val currentMilestone = uiModel.userProgression.currentMethodMilestone
//        val overallMilestone = UserProgression.OVERALL_METHOD_MILESTONE
//
//        text = context.getString(R.string.milestone_text_format, currentMilestone, overallMilestone)
//    }
//}

@BindingAdapter("weeklyAchievement")
fun TextView.bindWeeklyAchievement(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val totalCompletedSessionsCount = uiModel.sessions.filter { session ->
            session.isCompleted
        }.size

        text = context.getString(R.string.home_milestone_text_format, totalCompletedSessionsCount, LiftCategory.TOTAL_LIFT_CATEGORY_COUNT)
    }
}

private inline fun bindUiResult(uiResult: UiResult, block: (UiResult.Success) -> Unit) {
    if (uiResult !is UiResult.Success) {
        return
    } else {
        block(uiResult)
    }
}
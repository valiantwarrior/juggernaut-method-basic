package kr.valor.juggernaut.ui.home

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.*
import kr.valor.juggernaut.databinding.ItemHomeSessionSummaryBinding
import kr.valor.juggernaut.domain.progression.model.UserProgression
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

    binding.thumbnailImg.setImageResource(imageResource)
    binding.liftCategoryText.text = session.category.name
    binding.sessionSummaryInfoCardView.isEnabled = !session.isCompleted
}

@BindingAdapter("currentPhase")
fun TextView.bindPhase(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val phase = uiModel.userProgression.phase.name

        text = phase
    }
}

@BindingAdapter("currentMicroCycle")
fun TextView.bindMicroCycle(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val microCycle = uiModel.userProgression.microCycle.name

        text = microCycle
    }
}

@BindingAdapter("milestone")
fun TextView.bindCurrentMilestone(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val currentMilestone = uiModel.userProgression.currentMethodMilestone
        val overallMilestone = UserProgression.OVERALL_METHOD_MILESTONE

        text = context.getString(R.string.milestone_text_format, currentMilestone, overallMilestone)
    }
}

@BindingAdapter("weeklyAchievement")
fun TextView.bindWeeklyAchievement(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val totalCompletedSessionsCount = uiModel.sessions.filter { session ->
            session.isCompleted
        }.size

        text = context.getString(R.string.milestone_text_format, totalCompletedSessionsCount, LiftCategory.TOTAL_LIFT_CATEGORY_COUNT)
    }
}

private inline fun bindUiResult(uiResult: UiResult, block: (UiResult.Success) -> Unit) {
    if (uiResult !is UiResult.Success) {
        return
    } else {
        block(uiResult)
    }
}
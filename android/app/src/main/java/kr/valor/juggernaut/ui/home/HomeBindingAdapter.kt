package kr.valor.juggernaut.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.ui.home.sessionsummary.SessionSummaryAdapter

@BindingAdapter("sessionSummaries")
fun RecyclerView.bindSessions(uiResult: UiResult) {
    bindUiResult(uiResult) { uiModel ->
        val sessions = uiModel.sessions
        val adapter = adapter as SessionSummaryAdapter

        adapter.submitList(sessions)
    }
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
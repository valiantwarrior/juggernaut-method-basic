package kr.valor.juggernaut.ui.overall

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.home.detail.DetailAdapter
import kr.valor.juggernaut.ui.home.detail.DetailViewHolderItem

@BindingAdapter("overallSessions")
fun RecyclerView.bindSessions(uiState: OverallUiState) {
    bindUiState(uiState) { result ->
        val sessionSummaries = result.sessionSummaries
        if (sessionSummaries.isEmpty()) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE

            val detailViewHolderContentItems = sessionSummaries.map { sessionSummary ->
                DetailViewHolderItem.ContentItem(sessionSummary = sessionSummary)
            }

            (adapter as DetailAdapter).submitList(detailViewHolderContentItems)
        }
    }
}

@BindingAdapter("emptySession")
fun ConstraintLayout.bindEmptyState(uiState: OverallUiState) {
    bindUiState(uiState) { result ->
        val sessionSummaries = result.sessionSummaries

        visibility = if (sessionSummaries.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

private fun bindUiState(uiState: OverallUiState, block: (OverallUiState.Result) -> Unit) {
    if (uiState !is OverallUiState.Result) {
        return
    }
    block(uiState)
}
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
    bindUiState(uiState) { sessions ->
        if (sessions.isEmpty()) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE

            val detailViewHolderContentItems = sessions.map { session ->
                DetailViewHolderItem.ContentItem(session = session)
            }

            (adapter as DetailAdapter).submitList(detailViewHolderContentItems)
        }
    }
}

@BindingAdapter("emptySession")
fun ConstraintLayout.bindEmptyState(uiState: OverallUiState) {
    bindUiState(uiState) { sessions ->
        visibility = if (sessions.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

private fun bindUiState(uiState: OverallUiState, block: (List<Session>) -> Unit) {
    if (uiState !is OverallUiState.Result) {
        return
    }
    block(uiState.sessions)
}
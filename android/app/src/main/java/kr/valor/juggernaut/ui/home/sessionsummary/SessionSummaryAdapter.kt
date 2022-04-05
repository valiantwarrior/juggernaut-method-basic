package kr.valor.juggernaut.ui.home.sessionsummary

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.home.NavigationClickListener

class SessionSummaryAdapter(
    private val navigateClickListener: NavigationClickListener
): ListAdapter<SessionSummary, SessionSummaryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionSummaryViewHolder =
        SessionSummaryViewHolder.create(parent, navigateClickListener)

    override fun onBindViewHolder(holder: SessionSummaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SessionSummary>() {
            override fun areItemsTheSame(oldItem: SessionSummary, newItem: SessionSummary): Boolean {
                return oldItem.sessionId == newItem.sessionId
            }

            override fun areContentsTheSame(oldItem: SessionSummary, newItem: SessionSummary): Boolean {
                return oldItem == newItem
            }
        }
    }
}
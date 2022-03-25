package kr.valor.juggernaut.ui.home.sessionsummary

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.home.overview.NavigateClickListener

class SessionSummaryAdapter(
    private val navigateClickListener: NavigateClickListener
): ListAdapter<Session, SessionSummaryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionSummaryViewHolder =
        SessionSummaryViewHolder.create(parent, navigateClickListener)

    override fun onBindViewHolder(holder: SessionSummaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem.sessionId == newItem.sessionId
            }

            override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem == newItem
            }
        }
    }
}
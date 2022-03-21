package kr.valor.juggernaut.ui.home.sessionsummary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.databinding.ItemHomeSessionSummaryBinding
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.home.NavigateClickListener

class SessionSummaryViewHolder private constructor(
    private val binding: ItemHomeSessionSummaryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Session) {
        with(binding) {
            session = item
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup, navigateClickListener: NavigateClickListener): SessionSummaryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHomeSessionSummaryBinding.inflate(layoutInflater, parent, false)
            binding.navigateAction = navigateClickListener

            return SessionSummaryViewHolder(binding)
        }
    }

}
package kr.valor.juggernaut.ui.home.session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.databinding.ItemHomeSessionSummaryBinding
import kr.valor.juggernaut.domain.session.model.Session

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
        fun create(parent: ViewGroup): SessionSummaryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHomeSessionSummaryBinding.inflate(layoutInflater, parent, false)

            return SessionSummaryViewHolder(binding)
        }
    }

}
package kr.valor.juggernaut.ui.home.sessionsummary

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemHomeSessionSummaryBinding
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory
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

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, navigateClickListener: NavigateClickListener): SessionSummaryViewHolder {
            val binding =
                provideDataBinding<ItemHomeSessionSummaryBinding>(parent, R.layout.item_home_session_summary)
                    .apply { navigateAction = navigateClickListener }

            return SessionSummaryViewHolder(binding)
        }
    }

}
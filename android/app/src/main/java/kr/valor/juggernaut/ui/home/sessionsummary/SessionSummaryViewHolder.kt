package kr.valor.juggernaut.ui.home.sessionsummary

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemOverviewSessionSummaryBinding
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory
import kr.valor.juggernaut.ui.home.NavigationClickListener

class SessionSummaryViewHolder private constructor(
    private val binding: ItemOverviewSessionSummaryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SessionSummary) {
        with(binding) {
            sessionSummary = item
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, navigateClickListener: NavigationClickListener): SessionSummaryViewHolder {
            val binding =
                provideDataBinding<ItemOverviewSessionSummaryBinding>(parent, R.layout.item_overview_session_summary)
                    .apply { navigateAction = navigateClickListener }

            return SessionSummaryViewHolder(binding)
        }
    }

}
package kr.valor.juggernaut.ui.user

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemSessionAchievementInfoBinding
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory
import kr.valor.juggernaut.ui.home.NavigationClickListener

open class ItemUserSessionRecordViewHolder(
    private val binding: ItemSessionAchievementInfoBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SessionSummary) {
        with(binding) {
            sessionSummary = item
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, navigateClickListener: NavigationClickListener): ItemUserSessionRecordViewHolder {
            val binding =
                provideDataBinding<ItemSessionAchievementInfoBinding>(parent, R.layout.item_session_achievement_info)
                    .apply {
                        navigateAction = navigateClickListener
                    }

            return ItemUserSessionRecordViewHolder(binding)
        }
    }

}
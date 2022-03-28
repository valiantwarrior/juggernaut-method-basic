package kr.valor.juggernaut.ui.home.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemDetailSessionHeaderBinding
import kr.valor.juggernaut.databinding.ItemSessionAchievementInfoBinding
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory
import kr.valor.juggernaut.ui.home.NavigationClickListener
import kr.valor.juggernaut.ui.user.ItemUserSessionRecordViewHolder

class DetailContentViewHolder(
    binding: ItemSessionAchievementInfoBinding
): ItemUserSessionRecordViewHolder(binding) {

    companion object {
        fun create(parent: ViewGroup, navigateClickListener: NavigationClickListener): ItemUserSessionRecordViewHolder =
            ItemUserSessionRecordViewHolder.create(parent, navigateClickListener)
    }

}

class DetailHeaderViewHolder private constructor(
    private val binding: ItemDetailSessionHeaderBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserProgression) {
        with(binding) {
            userProgression = item
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): DetailHeaderViewHolder {
            val binding =
                provideDataBinding<ItemDetailSessionHeaderBinding>(parent, R.layout.item_detail_session_header)

            return DetailHeaderViewHolder(binding)
        }
    }

}
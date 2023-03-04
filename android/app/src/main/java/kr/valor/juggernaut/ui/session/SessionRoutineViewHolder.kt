package kr.valor.juggernaut.ui.session

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemSessionAmrapAchievementBinding
import kr.valor.juggernaut.databinding.ItemSessionAmrapRoutineBinding
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory

sealed class SessionRoutineViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)

open class RoutineViewHolder(
    private val binding: ViewDataBinding
): SessionRoutineViewHolder(binding) {

    open fun bind(item: RoutineItem) {
        with(binding as ItemSessionRoutineBinding) {
            routineItem = item
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): SessionRoutineViewHolder = RoutineViewHolder(
            provideDataBinding<ItemSessionRoutineBinding>(parent, R.layout.item_session_routine)
        )
    }

}


class FooterViewHolder private constructor(
    private val binding: ItemSessionFooterBinding
): SessionRoutineViewHolder(binding) {

    fun bind(item: FooterItem) {
        binding.apply {
            footerItem = item
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, footerAction: () -> Unit): SessionRoutineViewHolder =
            provideDataBinding<ItemSessionFooterBinding>(parent, R.layout.item_session_footer)
                .apply { footerActionButton.setOnClickListener { footerAction() } }
                .let(::FooterViewHolder)
    }

}

class AmrapRoutineViewHolder private constructor(
    private val binding: ItemSessionAmrapRoutineBinding
): RoutineViewHolder(binding) {

    override fun bind(item: RoutineItem) {
        with(binding) {
            amrapRoutineItem = item as AmrapRoutineItem
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, plusRepsAction: () -> Unit, minusRepsAction: () -> Unit): RoutineViewHolder =
            provideDataBinding<ItemSessionAmrapRoutineBinding>(parent, R.layout.item_session_amrap_routine)
                .apply {
                    plusRepsButton.setOnClickListener {
                        plusRepsAction()
                    }
                    minusRepsButton.setOnClickListener {
                        minusRepsAction()
                    }
                }
                .let(::AmrapRoutineViewHolder)
    }

}

class AmrapRoutineAchievementViewHolder private constructor(
    private val binding: ItemSessionAmrapAchievementBinding
): RoutineViewHolder(binding) {

    override fun bind(item: RoutineItem) {
        with(binding) {
            amrapRoutineItem = item as AmrapRoutineItem
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): RoutineViewHolder {
            val binding: ItemSessionAmrapAchievementBinding =
                provideDataBinding(parent, R.layout.item_session_amrap_achievement)

            return AmrapRoutineAchievementViewHolder(binding)
        }
    }
}
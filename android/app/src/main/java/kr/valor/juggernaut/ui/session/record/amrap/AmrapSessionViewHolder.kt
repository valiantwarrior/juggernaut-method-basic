package kr.valor.juggernaut.ui.session.record.amrap

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemSessionAmrapRoutineBinding
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory

sealed class AmrapSessionViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)

class AmrapSessionWarmupRoutineViewHolder private constructor(
    private val binding: ItemSessionRoutineBinding
): AmrapSessionViewHolder(binding) {

    fun bind() {
        binding.executePendingBindings()
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): AmrapSessionViewHolder {
            val binding =
                provideDataBinding<ItemSessionRoutineBinding>(parent, R.layout.item_session_routine)

            return AmrapSessionWarmupRoutineViewHolder(binding)
        }
    }
}

class AmrapSessionAmrapRoutineViewHolder(
    private val binding: ItemSessionAmrapRoutineBinding
): AmrapSessionViewHolder(binding) {

    fun bind() {
        binding.executePendingBindings()
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): AmrapSessionViewHolder {
            val binding =
                provideDataBinding<ItemSessionAmrapRoutineBinding>(parent, R.layout.item_session_amrap_routine)

            return AmrapSessionAmrapRoutineViewHolder(binding)
        }
    }
}

class AmrapSessionFooterViewHolder(
    private val binding: ItemSessionFooterBinding
): AmrapSessionViewHolder(binding) {

    fun bind() {
        binding.executePendingBindings()
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): AmrapSessionViewHolder {
            val binding =
                provideDataBinding<ItemSessionFooterBinding>(parent, R.layout.item_session_footer)

            return AmrapSessionFooterViewHolder(binding)
        }
    }
}
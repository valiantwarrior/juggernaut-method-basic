package kr.valor.juggernaut.ui.session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemSessionAmrapRoutineBinding
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory

sealed class SessionRoutineViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

open class RoutineViewHolder(
    private val binding: ViewDataBinding
): SessionRoutineViewHolder(binding) {

    open fun bind(item: RoutineItem) {
        (binding as ItemSessionRoutineBinding)
            .apply {
                routine = item.routine
                executePendingBindings()
            }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): SessionRoutineViewHolder {
            val binding =
                provideDataBinding<ItemSessionRoutineBinding>(parent, R.layout.item_session_routine)

            return RoutineViewHolder(binding)
        }
    }

}


class FooterViewHolder private constructor(
    binding: ItemSessionFooterBinding
): SessionRoutineViewHolder(binding) {

    companion object {
        fun create(parent: ViewGroup, submitAction: () -> Unit): SessionRoutineViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSessionFooterBinding.inflate(layoutInflater, parent, false)
                .apply {
                    submitButton.setOnClickListener {
                        submitAction()
                    }
                }

            return FooterViewHolder(binding)
        }
    }

}

class AmrapRoutineViewHolder private constructor(
    private val binding: ItemSessionAmrapRoutineBinding
): RoutineViewHolder(binding) {

    override fun bind(item: RoutineItem) {
        item as AmrapRoutineItem
        with(binding) {
            routine = item.routine
            repetitions = item.repetitions
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, plusRepsAction: () -> Unit, minusRepsAction: () -> Unit): RoutineViewHolder {
            val binding =
                provideDataBinding<ItemSessionAmrapRoutineBinding>(parent, R.layout.item_session_amrap_routine)
                    .apply {
                        plusRepsButton.setOnClickListener {
                            plusRepsAction()
                        }

                        minusRepsButton.setOnClickListener {
                            minusRepsAction()
                        }
                    }

            return AmrapRoutineViewHolder(binding)
        }
    }

}
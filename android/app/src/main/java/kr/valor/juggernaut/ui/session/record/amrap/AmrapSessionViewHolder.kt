package kr.valor.juggernaut.ui.session.record.amrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemSessionAmrapRoutineBinding
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory

sealed class AmrapSessionViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

class AmrapSessionWarmupRoutineViewHolder private constructor(
    private val binding: ItemSessionRoutineBinding
): AmrapSessionViewHolder(binding) {

    fun bind(item: AmrapSessionRecordItem.WarmupRoutineItem) {
        binding.routine = item.routine
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

class AmrapSessionAmrapRoutineViewHolder private constructor(
    private val binding: ItemSessionAmrapRoutineBinding
): AmrapSessionViewHolder(binding) {

    fun bind(item: AmrapSessionRecordItem.AmrapRoutineItem) {
        binding.routine = item.routine
        binding.repetitions = item.repetitions
        binding.executePendingBindings()
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup, plusRepsAction: () -> Unit, minusRepsAction: () -> Unit): AmrapSessionViewHolder {
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

            return AmrapSessionAmrapRoutineViewHolder(binding)
        }
    }
}

class AmrapSessionFooterViewHolder private constructor(
    binding: ItemSessionFooterBinding
): AmrapSessionViewHolder(binding) {

    companion object {
        fun create(parent: ViewGroup, submitAction: () -> Unit): AmrapSessionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSessionFooterBinding.inflate(layoutInflater, parent, false)
                .apply {
                    submitButton.setOnClickListener {
                        submitAction()
                    }
                }

            return AmrapSessionFooterViewHolder(binding)
        }
    }
}
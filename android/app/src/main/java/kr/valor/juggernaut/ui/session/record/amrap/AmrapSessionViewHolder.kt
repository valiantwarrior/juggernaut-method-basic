package kr.valor.juggernaut.ui.session.record.amrap

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.databinding.ItemSessionAmrapRoutineBinding
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding

sealed class AmrapSessionViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)

class AmrapSessionWarmupRoutineViewHolder(
    private val binding: ItemSessionRoutineBinding
): AmrapSessionViewHolder(binding) {

}

class AmrapSessionAmrapRoutineViewHolder(
    private val binding: ItemSessionAmrapRoutineBinding
): AmrapSessionViewHolder(binding) {

}

class AmrapSessionFooterViewHolder(
    private val binding: ItemSessionFooterBinding
): AmrapSessionViewHolder(binding) {

}
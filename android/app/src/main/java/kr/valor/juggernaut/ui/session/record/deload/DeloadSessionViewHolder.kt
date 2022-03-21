package kr.valor.juggernaut.ui.session.record.deload

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding

sealed class DeloadSessionViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)

class DeloadSessionRoutineViewHolder(
    private val binding: ItemSessionRoutineBinding
): DeloadSessionViewHolder(binding) {

}

class DeloadSessionFooterViewHolder(
    private val binding: ItemSessionFooterBinding
): DeloadSessionViewHolder(binding) {

}
package kr.valor.juggernaut.ui.session.record.deload

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.valor.juggernaut.databinding.ItemSessionFooterBinding
import kr.valor.juggernaut.databinding.ItemSessionRoutineBinding

sealed class DeloadSessionViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

class DeloadSessionRoutineViewHolder(
    private val binding: ItemSessionRoutineBinding
): DeloadSessionViewHolder(binding) {

}

class DeloadSessionFooterViewHolder(
    private val binding: ItemSessionFooterBinding
): DeloadSessionViewHolder(binding) {

}
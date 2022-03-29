package kr.valor.juggernaut.ui.statistic.trainingmax

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.databinding.ItemStatisticTrainingMaxBinding
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory

class TrainingMaxViewHolder(
    private val binding: ItemStatisticTrainingMaxBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TrainingMax) {
        with(binding) {
            trainingMax = item
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(parent: ViewGroup): TrainingMaxViewHolder {
            val binding =
                provideDataBinding<ItemStatisticTrainingMaxBinding>(parent, R.layout.item_statistic_training_max)

            return TrainingMaxViewHolder(binding)
        }
    }
}
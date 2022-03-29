package kr.valor.juggernaut.ui.statistic.trainingmax

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax

class TrainingMaxAdapter: ListAdapter<TrainingMax, TrainingMaxViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingMaxViewHolder =
        TrainingMaxViewHolder.create(parent)

    override fun onBindViewHolder(holder: TrainingMaxViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrainingMax>() {
            override fun areItemsTheSame(oldItem: TrainingMax, newItem: TrainingMax): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TrainingMax, newItem: TrainingMax): Boolean {
                return oldItem == newItem
            }
        }
    }
}
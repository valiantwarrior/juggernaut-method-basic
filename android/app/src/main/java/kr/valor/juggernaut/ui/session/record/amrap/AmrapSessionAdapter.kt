package kr.valor.juggernaut.ui.session.record.amrap

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.valor.juggernaut.domain.session.model.Routine

class AmrapSessionAdapter: ListAdapter<Routine, AmrapSessionViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmrapSessionViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AmrapSessionViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Routine>() {
            override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
                return oldItem == newItem
            }
        }
    }
}
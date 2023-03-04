package kr.valor.juggernaut.ui.session

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.valor.juggernaut.R

open class SessionRoutineAdapter(
    private val footerAction: () -> Unit
): ListAdapter<SessionRoutineItem, SessionRoutineViewHolder>(this) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRoutineViewHolder {
        return when(viewType) {
            R.layout.item_session_routine -> RoutineViewHolder.create(parent)
            R.layout.item_session_footer -> FooterViewHolder.create(parent, footerAction)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is RoutineItem -> R.layout.item_session_routine
            is FooterItem -> R.layout.item_session_footer
        }
    }

    final override fun onBindViewHolder(holder: SessionRoutineViewHolder, position: Int) {
        when(val item = getItem(position)) {
            is RoutineItem -> (holder as RoutineViewHolder).bind(item)
            is FooterItem -> (holder as FooterViewHolder).bind(item)
        }
    }

    companion object : DiffUtil.ItemCallback<SessionRoutineItem>(){
        override fun areItemsTheSame(oldItem: SessionRoutineItem, newItem: SessionRoutineItem) =
            oldItem.itemId == newItem.itemId

        override fun areContentsTheSame(oldItem: SessionRoutineItem, newItem: SessionRoutineItem) =
            oldItem == newItem
    }

}
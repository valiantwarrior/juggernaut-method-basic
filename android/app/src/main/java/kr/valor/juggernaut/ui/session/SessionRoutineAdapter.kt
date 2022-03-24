package kr.valor.juggernaut.ui.session

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

open class SessionRoutineAdapter(
    private val footerAction: () -> Unit
): ListAdapter<SessionRoutineItem, SessionRoutineViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRoutineViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_ROUTINE -> RoutineViewHolder.create(parent)
            ITEM_VIEW_TYPE_FOOTER -> FooterViewHolder.create(parent, footerAction)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is RoutineItem -> ITEM_VIEW_TYPE_ROUTINE
            is FooterItem -> ITEM_VIEW_TYPE_FOOTER
        }
    }

    final override fun onBindViewHolder(holder: SessionRoutineViewHolder, position: Int) {
        when(val item = getItem(position)) {
            is RoutineItem -> (holder as RoutineViewHolder).bind(item)
            is FooterItem -> (holder as FooterViewHolder).bind(item)
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_ROUTINE = 0
        const val ITEM_VIEW_TYPE_FOOTER = Int.MIN_VALUE

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SessionRoutineItem>() {
            override fun areItemsTheSame(oldItem: SessionRoutineItem, newItem: SessionRoutineItem): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: SessionRoutineItem, newItem: SessionRoutineItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
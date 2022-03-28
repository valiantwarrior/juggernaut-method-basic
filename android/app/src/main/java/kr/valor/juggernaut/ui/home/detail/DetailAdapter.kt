package kr.valor.juggernaut.ui.home.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.ui.home.NavigationClickListener
import kr.valor.juggernaut.ui.user.ItemUserSessionRecordViewHolder

class DetailAdapter(
    private val navigateClickListener: NavigationClickListener
): ListAdapter<DetailViewHolderItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is DetailViewHolderItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is DetailViewHolderItem.ContentItem -> ITEM_VIEW_TYPE_CONTENT
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> DetailHeaderViewHolder.create(parent)
            ITEM_VIEW_TYPE_CONTENT -> DetailContentViewHolder.create(parent, navigateClickListener)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = getItem(position)) {
            is DetailViewHolderItem.HeaderItem -> (holder as DetailHeaderViewHolder).bind(item.userProgression)
            is DetailViewHolderItem.ContentItem -> (holder as ItemUserSessionRecordViewHolder).bind(item.session)
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_CONTENT = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailViewHolderItem>() {
            override fun areItemsTheSame(oldItem: DetailViewHolderItem, newItem: DetailViewHolderItem): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: DetailViewHolderItem, newItem: DetailViewHolderItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
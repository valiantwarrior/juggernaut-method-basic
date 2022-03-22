package kr.valor.juggernaut.ui.session.record.amrap

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.ui.session.record.RecordUiAction

class AmrapSessionAdapter: ListAdapter<AmrapSessionRecordItem, AmrapSessionViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is AmrapSessionRecordItem.WarmupRoutineItem -> ITEM_VIEW_TYPE_WARMUP
            is AmrapSessionRecordItem.AmrapRoutineItem -> ITEM_VIEW_TYPE_AMRAP
            is AmrapSessionRecordItem.Footer -> ITEM_VIEW_TYPE_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmrapSessionViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_WARMUP -> AmrapSessionWarmupRoutineViewHolder.create(parent)
            ITEM_VIEW_TYPE_AMRAP -> AmrapSessionAmrapRoutineViewHolder.create(parent)
            ITEM_VIEW_TYPE_FOOTER -> AmrapSessionFooterViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: AmrapSessionViewHolder, position: Int) {
        when(getItem(position)) {
            is AmrapSessionRecordItem.WarmupRoutineItem -> (holder as AmrapSessionWarmupRoutineViewHolder).bind()
            is AmrapSessionRecordItem.AmrapRoutineItem -> (holder as AmrapSessionAmrapRoutineViewHolder).bind()
            is AmrapSessionRecordItem.Footer -> (holder as AmrapSessionFooterViewHolder).bind()
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_WARMUP = 0
        private const val ITEM_VIEW_TYPE_AMRAP = 1
        private const val ITEM_VIEW_TYPE_FOOTER = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AmrapSessionRecordItem>() {
            override fun areItemsTheSame(oldItem: AmrapSessionRecordItem, newItem: AmrapSessionRecordItem): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: AmrapSessionRecordItem, newItem: AmrapSessionRecordItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
package kr.valor.juggernaut.ui.session.record

import android.view.ViewGroup
import kr.valor.juggernaut.ui.session.*

class DeloadRoutineAdapter(submitAction: () -> Unit): SessionRoutineAdapter(submitAction)

class AmrapRoutineAdapter(
    private val plusRepsAction: () -> Unit,
    private val minusRepsAction: () -> Unit,
    submitAction: () -> Unit
): SessionRoutineAdapter(submitAction) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is AmrapRoutineItem -> ITEM_VIEW_TYPE_AMRAP_ROUTINE
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRoutineViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_AMRAP_ROUTINE -> {
                AmrapRoutineViewHolder.create(parent, plusRepsAction, minusRepsAction)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_AMRAP_ROUTINE = ITEM_VIEW_TYPE_ROUTINE + 1
    }

}
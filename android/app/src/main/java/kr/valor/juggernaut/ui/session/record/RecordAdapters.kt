package kr.valor.juggernaut.ui.session.record

import android.view.ViewGroup
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.session.*

class DeloadRoutineAdapter(submitAction: () -> Unit): SessionRoutineAdapter(submitAction)

class AmrapRoutineAdapter(
    private val plusRepsAction: () -> Unit,
    private val minusRepsAction: () -> Unit,
    submitAction: () -> Unit
): SessionRoutineAdapter(submitAction) {

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is AmrapRoutineItem -> R.layout.item_session_amrap_routine
        else -> super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRoutineViewHolder {
        return when(viewType) {
            R.layout.item_session_amrap_routine ->
                AmrapRoutineViewHolder.create(parent, plusRepsAction, minusRepsAction)

            else -> super.onCreateViewHolder(parent, viewType)
        }
    }
}
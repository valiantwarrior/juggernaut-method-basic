package kr.valor.juggernaut.ui.session.accomplishment

import android.view.ViewGroup
import kr.valor.juggernaut.ui.session.AmrapRoutineAchievementViewHolder
import kr.valor.juggernaut.ui.session.AmrapRoutineItem
import kr.valor.juggernaut.ui.session.SessionRoutineAdapter
import kr.valor.juggernaut.ui.session.SessionRoutineViewHolder

/**
 * Instead of footer-based action, I will use FAB
 */
class AccomplishmentAdapter: SessionRoutineAdapter({ /* nothing */ }) {
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is AmrapRoutineItem -> ITEM_VIEW_TYPE_AMRAP_ROUTINE_ACHIEVEMENT
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRoutineViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_AMRAP_ROUTINE_ACHIEVEMENT -> AmrapRoutineAchievementViewHolder.create(parent)
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_AMRAP_ROUTINE_ACHIEVEMENT = ITEM_VIEW_TYPE_ROUTINE + 1
    }
}
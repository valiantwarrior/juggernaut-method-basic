package kr.valor.juggernaut.ui.session.accomplishment

import android.view.ViewGroup
import kr.valor.juggernaut.R
import kr.valor.juggernaut.ui.session.AmrapRoutineAchievementViewHolder
import kr.valor.juggernaut.ui.session.AmrapRoutineItem
import kr.valor.juggernaut.ui.session.SessionRoutineAdapter
import kr.valor.juggernaut.ui.session.SessionRoutineViewHolder

/**
 * Instead of footer-based action, I will use FAB
 */
class AccomplishmentAdapter: SessionRoutineAdapter({ /* nothing */ }) {

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is AmrapRoutineItem -> R.layout.item_session_amrap_achievement
        else -> super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRoutineViewHolder =
        when(viewType) {
            R.layout.item_session_amrap_achievement -> AmrapRoutineAchievementViewHolder.create(parent)
            else -> super.onCreateViewHolder(parent, viewType)
        }
}
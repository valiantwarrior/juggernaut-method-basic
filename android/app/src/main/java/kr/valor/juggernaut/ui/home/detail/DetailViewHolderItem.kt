package kr.valor.juggernaut.ui.home.detail

import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionSummary

sealed class DetailViewHolderItem {

    abstract val itemId: Long

    data class HeaderItem(val userProgression: UserProgression): DetailViewHolderItem() {
        override val itemId: Long
            get() = Long.MIN_VALUE
    }

    data class ContentItem(val sessionSummary: SessionSummary): DetailViewHolderItem() {
        override val itemId: Long
            get() = sessionSummary.sessionId
    }

}
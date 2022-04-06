package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import java.time.LocalDateTime

data class SessionSummary (
    val sessionId: Long,
    val category: LiftCategory,
    val sessionProgression: SessionProgression,
    val completedLocalDateTime: LocalDateTime?,
    val amrapRoutine: Routine?
) {
    val isCompletedSession: Boolean
        get() = completedLocalDateTime != null
}
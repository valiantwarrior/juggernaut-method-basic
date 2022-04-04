package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import java.time.LocalDateTime

data class Session(
    val sessionId: Long,
    val category: LiftCategory,
    val tmWeights: Int,
    val sessionProgression: SessionProgression,
    val isCompleted: Boolean,
    val completedLocalDateTime: LocalDateTime?,
    val sessionOrdinal: Int?,
    val routines: List<Routine>,
) {

    val warmupRoutines: List<Routine>?
        get() = getOrNull(sessionProgression.isAmrapSession) { routines.dropLast(1) }

    val amrapRoutine: Routine?
        get() = getOrNull(sessionProgression.isAmrapSession) { routines.last() }

    val deloadRoutines: List<Routine>?
        get() = getOrNull(!sessionProgression.isAmrapSession) { routines }

    private inline fun <T> getOrNull(condition: Boolean, get: () -> T): T? =
        if (condition) get() else null

}
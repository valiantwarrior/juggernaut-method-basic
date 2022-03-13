package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

data class Session(
    val sessionId: Long,
    val category: LiftCategory,
    val tmWeights: Int,
    val progression: Progression,
    val routines: List<Routine>,
) {

    private val isDeloadSession: Boolean
        get() = progression.microCycle == MicroCycle.DELOAD

    val warmupRoutines: List<Routine>?
        get() = getOrNull(!isDeloadSession) { routines.dropLast(1) }

    val amrapRoutine: Routine?
        get() = getOrNull(!isDeloadSession) { routines.last() }

    val deloadRoutines: List<Routine>?
        get() = getOrNull(isDeloadSession) { routines }

    private inline fun <T> getOrNull(condition: Boolean, get: () -> T): T? =
        if (condition) get() else null

    data class Progression(
        val methodCycle: MethodCycle,
        val phase: Phase,
        val microCycle: MicroCycle
    )
}
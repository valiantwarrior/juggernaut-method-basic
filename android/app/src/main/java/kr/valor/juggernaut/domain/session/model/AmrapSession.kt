package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase

data class AmrapSession(
    override val sessionId: Long,
    override val category: LiftCategory,
    override val tmWeights: Double,
    override val routines: AmrapSessionRoutine,
    val progressions: Progressions
): Session() {
    data class Progressions(
        val phase: Phase,
        val microCycle: MicroCycle
    )

    data class AmrapSessionRoutine(
        val warmupRoutines: List<Routine>,
        val amrapRoutine: Routine
    ): SessionRoutine
}
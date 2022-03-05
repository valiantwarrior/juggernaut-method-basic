package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.Phase

data class DeloadSession(
    override val sessionId: Long,
    override val category: LiftCategory,
    override val tmWeights: Double,
    override val routines: DeloadSessionRoutine,
    val phase: Phase
): Session() {
    data class DeloadSessionRoutine(
        val routines: List<Routine>
    ): SessionRoutine
}

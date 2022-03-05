package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.common.LiftCategory

abstract class Session {
    abstract val sessionId: Long

    abstract val category: LiftCategory

    abstract val tmWeights: Double

    abstract val routines: SessionRoutine
}
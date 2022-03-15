package kr.valor.juggernaut.common

@JvmInline
value class MethodCycle(val value: Int) {
    companion object {
        const val INITIAL = 1
    }
}

enum class Phase {
    REP10, REP8, REP5, REP3;

    companion object {
        val INITIAL = REP10
    }
}

enum class MicroCycle {
    ACCUMULATION, INTENSIFICATION, REALIZATION, DELOAD;

    companion object {
        val INITIAL = ACCUMULATION
    }
}

enum class MethodProgressState {
    NONE, ONGOING, DONE
}

/**
 * ORDER : BP -> SQ -> OHP -> DL
 */
enum class LiftCategory {
    /**
     * BP
     */
    BENCHPRESS,

    /**
     * SQ
     */
    SQUAT,

    /**
     * OHP
     */
    OVERHEADPRESS,

    /**
     * DL
     */
    DEADLIFT;

    companion object {
        const val TOTAL_LIFT_CATEGORY_COUNT = 4
    }
}
package kr.valor.juggernaut.common

sealed interface ProgressionElement

@JvmInline
value class MethodCycle(val value: Int): ProgressionElement {
    companion object {
        const val INITIAL = 1
    }
}

enum class Phase: ProgressionElement {
    REP10, REP8, REP5, REP3;

    companion object {
        val INITIAL = REP10
    }
}

enum class MicroCycle: ProgressionElement {
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
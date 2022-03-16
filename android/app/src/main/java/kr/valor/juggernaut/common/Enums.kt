package kr.valor.juggernaut.common

@JvmInline
value class MethodCycle(val value: Int) {
    companion object {
        const val INITIAL_VALUE = 1

        val INITIAL = MethodCycle(INITIAL_VALUE)

        operator fun MethodCycle.plus(other: Int): MethodCycle =
            MethodCycle(this.value + other)

        operator fun MethodCycle.minus(other: Int): MethodCycle =
            MethodCycle(this.value - other)
    }
}

enum class Phase {
    REP10, REP8, REP5, REP3;

    companion object {
        val INITIAL = REP10
        val FINAL = REP3
    }
}

enum class MicroCycle {
    ACCUMULATION, INTENSIFICATION, REALIZATION, DELOAD;

    companion object {
        val INITIAL = ACCUMULATION
        val FINAL = DELOAD
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
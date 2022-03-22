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

enum class Phase(val baseAmrapRepetitions: Int) {
    REP10(Phase.REP10_AMRAP_REPETITIONS),
    REP8(Phase.REP8_AMRAP_REPETITIONS),
    REP5(Phase.REP5_AMRAP_REPETITIONS),
    REP3(Phase.REP3_AMRAP_REPETITIONS);

    companion object {
        val INITIAL = REP10
        val FINAL = REP3

        private const val REP10_AMRAP_REPETITIONS = 10
        private const val REP8_AMRAP_REPETITIONS = 8
        private const val REP5_AMRAP_REPETITIONS = 5
        private const val REP3_AMRAP_REPETITIONS = 3
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
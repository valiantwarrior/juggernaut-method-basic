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
        const val TOTAL_PHASE_COUNT = 4
        val INITIAL = REP10
        val FINAL = REP3

        private const val REP10_AMRAP_REPETITIONS = 10
        private const val REP8_AMRAP_REPETITIONS = 8
        private const val REP5_AMRAP_REPETITIONS = 5
        private const val REP3_AMRAP_REPETITIONS = 3
    }
}

enum class MicroCycle(val abbreviatedName: String) {
    ACCUMULATION("A"),
    INTENSIFICATION("I"),
    REALIZATION("R"),
    DELOAD("D");

    companion object {
        const val  TOTAL_MICROCYCLE_COUNT = 4
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
enum class LiftCategory(val baseIncrement: Double) {
    /**
     * BP
     */
    BENCHPRESS(LiftCategory.BASE_INCREMENT_UPPER_BODY_EXERCISE),

    /**
     * SQ
     */
    SQUAT(LiftCategory.BASE_INCREMENT_LOWER_BODY_EXERCISE),

    /**
     * OHP
     */
    OVERHEADPRESS(LiftCategory.BASE_INCREMENT_UPPER_BODY_EXERCISE),

    /**
     * DL
     */
    DEADLIFT(LiftCategory.BASE_INCREMENT_LOWER_BODY_EXERCISE);

    companion object {
        const val TOTAL_LIFT_CATEGORY_COUNT = 4
        private const val BASE_INCREMENT_LOWER_BODY_EXERCISE = 2.0
        private const val BASE_INCREMENT_UPPER_BODY_EXERCISE = 1.0

        const val KG_FACTOR = 1.0
        const val LBS_FACTOR = 2.5
    }
}
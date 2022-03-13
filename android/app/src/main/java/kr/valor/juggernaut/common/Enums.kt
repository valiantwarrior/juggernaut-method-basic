package kr.valor.juggernaut.common

sealed interface ProgressionElement

@JvmInline
value class MethodCycle(val value: Int): ProgressionElement

enum class Phase: ProgressionElement {
    REP10, REP8, REP5, REP3;
}

enum class MicroCycle: ProgressionElement {
    ACCUMULATION, INTENSIFICATION, REALIZATION, DELOAD
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
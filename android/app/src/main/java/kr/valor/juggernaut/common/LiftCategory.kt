package kr.valor.juggernaut.common

/**
 * ORDER : BP -> SQ -> OHP -> DL
 */
enum class LiftCategory(val baseIncrement: Double, val abbreviatedName: String) {
    /**
     * BP
     */
    BENCHPRESS(LiftCategory.BASE_INCREMENT_UPPER_BODY_EXERCISE, "BP"),

    /**
     * SQ
     */
    SQUAT(LiftCategory.BASE_INCREMENT_LOWER_BODY_EXERCISE, "SQ"),

    /**
     * OHP
     */
    OVERHEADPRESS(LiftCategory.BASE_INCREMENT_UPPER_BODY_EXERCISE, "OHP"),

    /**
     * DL
     */
    DEADLIFT(LiftCategory.BASE_INCREMENT_LOWER_BODY_EXERCISE, "DL");

    companion object {
        const val TOTAL_LIFT_CATEGORY_COUNT = 4
        private const val BASE_INCREMENT_LOWER_BODY_EXERCISE = 2.0
        private const val BASE_INCREMENT_UPPER_BODY_EXERCISE = 1.0

        const val KG_FACTOR = 1.0
        const val LBS_FACTOR = 2.5
    }
}
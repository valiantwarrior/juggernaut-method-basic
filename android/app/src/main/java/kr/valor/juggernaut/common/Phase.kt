package kr.valor.juggernaut.common

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
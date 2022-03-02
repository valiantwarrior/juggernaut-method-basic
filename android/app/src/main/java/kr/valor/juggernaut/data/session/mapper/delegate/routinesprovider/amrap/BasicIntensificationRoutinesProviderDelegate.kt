package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import java.math.BigDecimal

class BasicIntensificationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {
    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutinesIntensities()

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity()

    override val routinesPropertyMediateAction: (Double) -> Double =
        routinesPropertyMediateDelegate::mediate

    private fun initWarmupRoutinesIntensities(): Map<Phase, List<RoutineIntensity>> =
        mutableMapOf<Phase, List<RoutineIntensity>>().apply {
            Phase.values().forEach { phase ->
                val warmupRoutines = initRoutinesIntensities(phase)
                put(phase, warmupRoutines)
            }
        }.toMap()

    private fun initAmrapRoutineIntensity(): Map<Phase, RoutineIntensity> =
        mutableMapOf<Phase, RoutineIntensity>().apply {
            Phase.values().forEach { phase ->
                val amrapRoutine = RoutineIntensity(
                    intensityPercentages = getAmrapRoutineIntensity(phase),
                    repetitions = getAmrapRoutineRepetitions(phase)
                )
                put(phase, amrapRoutine)
            }
        }.toMap()

    private fun initRoutinesIntensities(phase: Phase): List<RoutineIntensity> {
        val warmupRoutinesRepetitions = getWarmupRoutinesRepetitions(phase)
        val mainRoutinesRepetitions = getMainRoutinesRepetitions(phase)
        val baseWarmupRoutinesIntensity= getBaseWarmupRoutinesIntensity(phase)

        val warmupRoutinesIntensities =
            (baseWarmupRoutinesIntensity to
                    baseWarmupRoutinesIntensity + INTENSIFICATION_WARMUP_INTENSITY_INCREASE_PER_SET).toList()

        val mainRoutinesIntensity = getMainRoutinesIntensity(phase)

        val mainRoutinesNumber = getMainRoutinesNumber(phase)

        return mutableListOf<RoutineIntensity>().apply {
            repeat(INTENSIFICATION_WARMUP_ROUTINE_NUMBER) { index ->
                val warmupRoutineIntensity = RoutineIntensity(
                    intensityPercentages = warmupRoutinesIntensities[index],
                    repetitions = warmupRoutinesRepetitions
                )
                add(warmupRoutineIntensity)
            }
            repeat(mainRoutinesNumber) {
                val mainRoutineIntensity = RoutineIntensity(
                    intensityPercentages = mainRoutinesIntensity,
                    repetitions = mainRoutinesRepetitions
                )
                add(mainRoutineIntensity)
            }
        }.toList()
    }

    private fun getWarmupRoutinesRepetitions(phase: Phase): Int =
        when(phase) {
            REP10 -> INTENSIFICATION_CYCLE_PHASE_REP10_WARMUP_OVERALL_REPETITIONS
            REP8 -> INTENSIFICATION_CYCLE_PHASE_REP8_WARMUP_OVERALL_REPETITIONS
            REP5 -> INTENSIFICATION_CYCLE_PHASE_REP5_WARMUP_OVERALL_REPETITIONS
            REP3 -> INTENSIFICATION_CYCLE_PHASE_REP3_WARMUP_OVERALL_REPETITIONS
        }

    private fun getWarmupRoutinesIncreaseFactor(phase: Phase): Double =
        when(phase) {
            REP10 -> 0.0
            REP8 -> INTENSIFICATION_WARMUP_INTENSITY_INCREASE_PER_PHASE * 1
            REP5 -> INTENSIFICATION_WARMUP_INTENSITY_INCREASE_PER_PHASE * 2
            REP3 -> INTENSIFICATION_WARMUP_INTENSITY_INCREASE_PER_PHASE * 3
        }

    private fun getBaseWarmupRoutinesIntensity(phase: Phase) =
        getWarmupRoutinesIncreaseFactor(phase) + INTENSIFICATION_BASE_INTENSITY

    private fun getMainRoutinesRepetitions(phase: Phase): Int =
        when(phase) {
            REP10 -> INTENSIFICATION_CYCLE_PHASE_REP10_MAIN_OVERALL_REPETITIONS
            REP8 -> INTENSIFICATION_CYCLE_PHASE_REP8_MAIN_OVERALL_REPETITIONS
            REP5 -> INTENSIFICATION_CYCLE_PHASE_REP5_MAIN_OVERALL_REPETITIONS
            REP3 -> INTENSIFICATION_CYCLE_PHASE_REP3_MAIN_OVERALL_REPETITIONS
        }

    private fun getMainRoutinesNumber(phase: Phase): Int =
        when(phase) {
            REP10 -> INTENSIFICATION_PHASE_REP10_MAIN_ROUTINE_NUMBER
            REP8 -> INTENSIFICATION_PHASE_REP8_MAIN_ROUTINE_NUMBER
            REP5 -> INTENSIFICATION_PHASE_REP5_MAIN_ROUTINE_NUMBER
            REP3 -> INTENSIFICATION_PHASE_REP3_MAIN_ROUTINE_NUMBER
        }

    private fun getMainRoutinesIntensity(phase: Phase) =
        getBaseWarmupRoutinesIntensity(phase) + INTENSIFICATION_MAIN_INTENSITY_INCREASE

    private fun getAmrapRoutineIntensity(phase: Phase) =
        getMainRoutinesIntensity(phase)

    private fun getAmrapRoutineRepetitions(phase: Phase) =
        getMainRoutinesRepetitions(phase)

    companion object {
        private const val INTENSIFICATION_BASE_INTENSITY = 0.55
        private const val INTENSIFICATION_WARMUP_ROUTINE_NUMBER = 2
        private const val INTENSIFICATION_WARMUP_INTENSITY_INCREASE_PER_SET = 0.075
        private const val INTENSIFICATION_WARMUP_INTENSITY_INCREASE_PER_PHASE = 0.05
        private const val INTENSIFICATION_MAIN_INTENSITY_INCREASE = 0.125
        private const val INTENSIFICATION_PHASE_REP10_MAIN_ROUTINE_NUMBER = 2
        private const val INTENSIFICATION_PHASE_REP8_MAIN_ROUTINE_NUMBER = 2
        private const val INTENSIFICATION_PHASE_REP5_MAIN_ROUTINE_NUMBER = 3
        private const val INTENSIFICATION_PHASE_REP3_MAIN_ROUTINE_NUMBER = 4
    }
}
package kr.valor.juggernaut.data.session.mapper.delegate.provider

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.Phase.*

import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate

class BasicAccumulationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutinesIntensities()

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity()

    override val routinesPropertyMediateAction: (Double) -> Double =
        routinesPropertyMediateDelegate::mediate

    private fun initWarmupRoutinesIntensities(): Map<Phase, List<RoutineIntensity>> {
        return mutableMapOf<Phase, List<RoutineIntensity>>().apply {
            ACCUMULATION_CYCLE_ROUTINE_INTENSITY_TABLE.forEach {
                val phase = it.phase
                val warmupRoutinesIntensities = initRoutinesIntensities(
                    intensity = it.intensityPercentage,
                    repetitions = it.repetitions,
                    sets = it.numberOfWarmupRoutines
                )
                put(phase, warmupRoutinesIntensities)
            }
        }.toMap()
    }

    private fun initAmrapRoutineIntensity(): Map<Phase, RoutineIntensity> {
        return mutableMapOf<Phase, RoutineIntensity>().apply {
            ACCUMULATION_CYCLE_ROUTINE_INTENSITY_TABLE.forEach {
                val phase = it.phase
                val amrapRoutineIntensity = RoutineIntensity(
                    intensityPercentages = it.intensityPercentage,
                    repetitions = it.repetitions
                )
                put(phase, amrapRoutineIntensity)
            }
        }.toMap()
    }

    private fun initRoutinesIntensities(intensity: Double, repetitions: Int, sets: Int): List<RoutineIntensity> =
        mutableListOf<RoutineIntensity>().apply {
            repeat(sets) {
                val routineIntensity = RoutineIntensity(
                    intensityPercentages = intensity,
                    repetitions = repetitions
                )
                add(routineIntensity)
            }
        }.toList()

    private data class AccumulationRoutineIntensity(
        val phase: Phase,
        val intensityPercentage: Double,
        val repetitions: Int,
        val numberOfWarmupRoutines: Int
    )

    companion object {
        private val ACCUMULATION_CYCLE_ROUTINE_INTENSITY_TABLE = listOf(
            AccumulationRoutineIntensity(
                phase = REP10,
                intensityPercentage = ACCUMULATION_CYCLE_PHASE_REP10_INTENSITY,
                repetitions = ACCUMULATION_CYCLE_PHASE_REP10_REPETITIONS,
                numberOfWarmupRoutines = ACCUMULATION_CYCLE_PHASE_REP10_SETS
            ),

            AccumulationRoutineIntensity(
                phase = REP8,
                intensityPercentage = ACCUMULATION_CYCLE_PHASE_REP8_INTENSITY,
                repetitions = ACCUMULATION_CYCLE_PHASE_REP8_REPETITIONS,
                numberOfWarmupRoutines = ACCUMULATION_CYCLE_PHASE_REP8_SETS
            ),

            AccumulationRoutineIntensity(
                phase = REP5,
                intensityPercentage = ACCUMULATION_CYCLE_PHASE_REP5_INTENSITY,
                repetitions = ACCUMULATION_CYCLE_PHASE_REP5_REPETITIONS,
                numberOfWarmupRoutines = ACCUMULATION_CYCLE_PHASE_REP5_SETS
            ),

            AccumulationRoutineIntensity(
                phase = REP3,
                intensityPercentage = ACCUMULATION_CYCLE_PHASE_REP3_INTENSITY,
                repetitions = ACCUMULATION_CYCLE_PHASE_REP3_REPETITIONS,
                numberOfWarmupRoutines = ACCUMULATION_CYCLE_PHASE_REP3_SETS
            )
        )
    }
}
package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.MicroCycle.*
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.model.SessionEntity
import kr.valor.juggernaut.domain.session.model.*
import kotlin.math.ceil

interface Mapper<I, O> {
    fun map(input: I): O
}

interface EntityModelMapper<I, O>: Mapper<I, O>

class DefaultEntityModelMapper(
    routinesProviderDelegate: RoutinesProviderDelegate
) : EntityModelMapper<SessionEntity, Session>, RoutinesProviderDelegate by routinesProviderDelegate {

    override fun map(input: SessionEntity): Session {
        return with(input) {
            val phase = Phase.valueOf(phaseName)
            val microCycle = MicroCycle.valueOf(microCycleName)
            val liftCategory = LiftCategory.valueOf(liftCategoryName)
            val routines = provideRoutines(phase, tmWeights)

            Session(
               sessionId = id,
               category = liftCategory,
               progressions = Progressions(phase, microCycle),
               tmWeights = tmWeights,
               routines = routines
            )
        }
    }
}


class DefaultRoutinesProviderDelegateFactory: RoutinesProviderDelegateFactory {
    override fun provide(progressions: Progressions, ceilUserPreferences: Double?): RoutinesProviderDelegate {
        return when(progressions.microCycle) {
            ACCUMULATION -> AccumulationRoutinesProviderDelegate(DefaultPropertyDelegate)
            INTENSIFICATION -> IntensificationRoutinesProviderDelegate(DefaultPropertyDelegate)
            REALIZATION -> RealizationRoutinesProviderDelegate(DefaultPropertyDelegate)
            DELOAD -> DeloadRoutinesProviderDelegate(DefaultPropertyDelegate)
        }
    }
}


class AccumulationRoutinesProviderDelegate(
    routinesPropertyDelegate: RoutinesPropertyDelegate
): RoutinesProviderDelegate, RoutinesPropertyDelegate by routinesPropertyDelegate {

    private val Phase.baseTrainingLoad: AccumulationTrainingLoad
        get() = when(this) {
            REP10 -> {
                AccumulationTrainingLoad(
                    setCounts = ACCUMULATION_CYCLE_PHASE_REP10_SETS,
                    repetitions = ACCUMULATION_CYCLE_PHASE_REP10_REPETITIONS,
                    intensity = ACCUMULATION_CYCLE_PHASE_REP10_INTENSITY
                )
            }
            REP8 -> {
                AccumulationTrainingLoad(
                    setCounts = ACCUMULATION_CYCLE_PHASE_REP8_SETS,
                    repetitions = ACCUMULATION_CYCLE_PHASE_REP8_REPETITIONS,
                    intensity = ACCUMULATION_CYCLE_PHASE_REP8_INTENSITY
                )
            }
            REP5 -> {
                AccumulationTrainingLoad(
                    setCounts = ACCUMULATION_CYCLE_PHASE_REP5_SETS,
                    repetitions = ACCUMULATION_CYCLE_PHASE_REP5_REPETITIONS,
                    intensity = ACCUMULATION_CYCLE_PHASE_REP5_INTENSITY
                )
            }
            REP3 -> {
                AccumulationTrainingLoad(
                    setCounts = ACCUMULATION_CYCLE_PHASE_REP3_SETS,
                    repetitions = ACCUMULATION_CYCLE_PHASE_REP3_REPETITIONS,
                    intensity = ACCUMULATION_CYCLE_PHASE_REP3_INTENSITY
                )
            }
        }

    override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        return with(phase.baseTrainingLoad) {
            val routineWeights = ceiling(tmWeights * intensity)
            val routineRepetitions = repetitions

            val warmupRoutines = mutableListOf<Routine>()
                .apply {
                    repeat(setCounts) {
                        val warmupRoutine = Routine(routineWeights, routineRepetitions)
                        add(warmupRoutine)
                    }
                }

            val amrapRoutine = Routine(routineWeights, routineRepetitions)

            SessionRoutine(
                warmupRoutines = warmupRoutines,
                amrapRoutine = amrapRoutine
            )
        }
    }

    private data class AccumulationTrainingLoad(
        val setCounts: Int,
        val repetitions: Int,
        val intensity: Double
    )
}

class IntensificationRoutinesProviderDelegate(
    routinesPropertyDelegate: RoutinesPropertyDelegate
): RoutinesProviderDelegate, RoutinesPropertyDelegate by routinesPropertyDelegate {

    private val Phase.baseTrainingLoad: IntensificationTrainingLoad
        get() =
            when (this) {
                REP10 -> {
                    val intensitiesChart = listOf(
                        Intensity(intensity = 0.55, 5),
                        Intensity(intensity = 0.625, 5),
                        Intensity(intensity = 0.675, 10),
                        Intensity(intensity = 0.675, 10),
                        Intensity(intensity = 0.675, 10)
                    )
                    val setCounts = PHASE_REP10_WARMUP_COUNT
                    IntensificationTrainingLoad(setCounts, intensitiesChart)
                }
                REP8 -> {
                    val intensitiesChart = listOf(
                        Intensity(intensity = 0.6, 3),
                        Intensity(intensity = 0.675, 3),
                        Intensity(intensity = 0.725, 8),
                        Intensity(intensity = 0.725, 8),
                        Intensity(intensity = 0.725, 8)
                    )
                    val setCounts = PHASE_REP8_WARMUP_COUNT
                    IntensificationTrainingLoad(setCounts, intensitiesChart)
                }

                REP5 -> {
                    val intensitiesChart = listOf(
                        Intensity(intensity = 0.65, 2),
                        Intensity(intensity = 0.725, 2),
                        Intensity(intensity = 0.775, 5),
                        Intensity(intensity = 0.775, 5),
                        Intensity(intensity = 0.775, 5),
                        Intensity(intensity = 0.775, 5)
                    )
                    val setCounts = PHASE_REP5_WARMUP_COUNT
                    IntensificationTrainingLoad(setCounts, intensitiesChart)
                }
                REP3 -> {
                    val intensitiesChart = listOf(
                        Intensity(intensity = 0.7, 1),
                        Intensity(intensity = 0.775, 1),
                        Intensity(intensity = 0.825, 3),
                        Intensity(intensity = 0.825, 3),
                        Intensity(intensity = 0.825, 3),
                        Intensity(intensity = 0.825, 3),
                        Intensity(intensity = 0.825, 3)
                    )
                    val setCounts = PHASE_REP3_WARMUP_COUNT
                    IntensificationTrainingLoad(setCounts, intensitiesChart)
                }
            }


    override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        TODO()
    }

    private data class IntensificationTrainingLoad(
        val setCounts: Int,
        val intensities: List<Intensity>
    )

    private data class Intensity(
        val intensity: Double,
        val repetitions: Int
    )
}

class RealizationRoutinesProviderDelegate(
    routinesPropertyDelegate: RoutinesPropertyDelegate
): RoutinesProviderDelegate, RoutinesPropertyDelegate by routinesPropertyDelegate {
    override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        TODO("Not yet implemented")
    }
}

class DeloadRoutinesProviderDelegate(
    routinesPropertyDelegate: RoutinesPropertyDelegate
): RoutinesProviderDelegate, RoutinesPropertyDelegate by routinesPropertyDelegate {
    override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        TODO("Not yet implemented")
    }
}



object DefaultPropertyDelegate: RoutinesPropertyDelegate {
    override fun ceiling(input: Double): Double =
        ceil(input)
}

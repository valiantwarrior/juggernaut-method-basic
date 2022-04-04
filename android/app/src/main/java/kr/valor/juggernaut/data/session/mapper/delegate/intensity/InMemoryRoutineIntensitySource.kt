package kr.valor.juggernaut.data.session.mapper.delegate.intensity

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.MicroCycle.*
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.domain.session.model.RoutineIntensity
import javax.inject.Inject
import javax.inject.Singleton

private typealias InMemoryRoutineIntensityItem = Pair<Int, Double>

private fun InMemoryRoutineIntensityItem.toRoutineIntensityModel(): RoutineIntensity =
    RoutineIntensity(first, second)

private fun List<InMemoryRoutineIntensityItem>.toRoutineIntensityModel(): List<RoutineIntensity> =
    map { it.toRoutineIntensityModel() }

private fun List<InMemoryRoutineIntensityItem>.toRoutineIntensityModelMap(): Map<Phase, List<RoutineIntensity>> =
    Phase.values().associate { phase ->
        phase to toRoutineIntensityModel()
    }


@Singleton
class InMemoryRoutineIntensitySource @Inject constructor() : RoutineIntensitySource<MicroCycle, Phase> {

    override fun provideRoutineIntensityMap(key: MicroCycle): Map<Phase, List<RoutineIntensity>> {
        return when(key) {
            ACCUMULATION -> ROUTINE_INTENSITY_ACCUMULATION
            INTENSIFICATION -> ROUTINE_INTENSITY_INTENSIFICATION
            REALIZATION -> ROUTINE_INTENSITY_REALIZATION
            DELOAD -> ROUTINE_INTENSITY_DELOAD
        }
    }


    companion object {
        private val ROUTINE_INTENSITY_ACCUMULATION: Map<Phase, List<RoutineIntensity>> = mapOf(
            REP10   to listOf(10 to 0.6, 10 to 0.6, 10 to 0.6, 10 to 0.6, 10 to 0.6),
            REP8    to listOf(8 to 0.65, 8 to 0.65, 8 to 0.65, 8 to 0.65, 8 to 0.65),
            REP5    to listOf(5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7),
            REP3    to listOf(3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75)
        ).mapValues { it.value.toRoutineIntensityModel() }

        private val ROUTINE_INTENSITY_INTENSIFICATION: Map<Phase, List<RoutineIntensity>> = mapOf(
            REP10   to listOf(5 to 0.55, 5 to 0.625, 10 to 0.675, 10 to 0.675, 10 to 0.675),
            REP8    to listOf(3 to 0.6, 3 to 0.675, 8 to 0.725, 8 to 0.725, 8 to 0.725),
            REP5    to listOf(2 to 0.65, 2 to 0.725, 5 to 0.775, 5 to 0.775, 5 to 0.775, 5 to 0.775),
            REP3    to listOf(1 to 0.7, 1 to 0.775, 3 to 0.825, 3 to 0.825, 3 to 0.825, 3 to 0.825, 3 to 0.825)
        ).mapValues { it.value.toRoutineIntensityModel() }

        private val ROUTINE_INTENSITY_REALIZATION: Map<Phase, List<RoutineIntensity>> = mapOf(
            REP10   to listOf(5 to 0.5, 3 to 0.6, 1 to 0.7, 10 to 0.75),
            REP8    to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 8 to 0.8),
            REP5    to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 1 to 0.8, 5 to 0.85),
            REP3    to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 1 to 0.8, 1 to 0.85, 3 to 0.9)
        ).mapValues { it.value.toRoutineIntensityModel() }

        private val ROUTINE_INTENSITY_DELOAD: Map<Phase, List<RoutineIntensity>> = listOf(
            5 to 0.4, 5 to 0.5, 5 to 0.6
        ).toRoutineIntensityModelMap()
    }
}
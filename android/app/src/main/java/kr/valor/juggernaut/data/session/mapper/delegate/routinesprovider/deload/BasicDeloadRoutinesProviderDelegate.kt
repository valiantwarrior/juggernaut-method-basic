package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.deload

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.createPhaseBasedKeyMapAndReturn
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.*
import kr.valor.juggernaut.domain.session.model.DeloadSession.DeloadSessionRoutine as DeloadSessionRoutine


class BasicDeloadRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): BasicMethodRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    private val deloadRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initDeloadRoutineIntensity()

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate

    override fun provideRoutines(phase: Phase, tmWeights: Double): DeloadSessionRoutine {
        val deloadRoutineIntensitiesOfCurrentPhase = deloadRoutinesIntensities[phase]!!
        val transform = routinesPropertyMediateAction

        return DeloadSessionRoutine(
            routines = deloadRoutineIntensitiesOfCurrentPhase.createRoutine(tmWeights, transform)
        )
    }

    private fun initDeloadRoutineIntensity(): Map<Phase, List<RoutineIntensity>> =
        createPhaseBasedKeyMapAndReturn { phase ->
            entireRoutineIntensityTable[phase]!!.toRoutineIntensityModel()
        }


    companion object: RoutineIntensityTableFactory {
        override val entireRoutineIntensityTable: PhaseEntireRoutineIntensityTable by lazy {
            createPhaseBasedKeyMapAndReturn { phaseRoutineIntensityItems }
        }

        private val phaseRoutineIntensityItems = listOf(
            PhaseRoutineIntensityItem(5, 0.4),
            PhaseRoutineIntensityItem(5, 0.5),
            PhaseRoutineIntensityItem(5, 0.6)
        )
    }
}
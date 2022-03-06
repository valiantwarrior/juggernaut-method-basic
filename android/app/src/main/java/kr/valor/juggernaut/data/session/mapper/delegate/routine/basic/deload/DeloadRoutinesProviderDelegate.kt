package kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.deload

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.createPhaseBasedKeyMapAndReturn
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.BasicMethodRoutinesProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.common.RoutineIntensityFactory
import kr.valor.juggernaut.domain.session.model.RoutineIntensity
import kr.valor.juggernaut.domain.session.model.SessionRoutine
import kr.valor.juggernaut.domain.session.model.DeloadSession.DeloadSessionRoutine as DeloadSessionRoutine


class DeloadRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): BasicMethodRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    private val deloadRoutineIntensities: Map<Phase, List<RoutineIntensity>> =
        routineIntensityMap

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate

    override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        val deloadRoutineIntensitiesOfCurrentPhase = deloadRoutineIntensities[phase]!!
        val transform = routinesPropertyMediateAction

        return DeloadSessionRoutine(
            routines = deloadRoutineIntensitiesOfCurrentPhase.createRoutine(tmWeights, transform)
        )
    }


    companion object: RoutineIntensityFactory() {
        override val routineIntensityMap: Map<Phase, List<RoutineIntensity>> by lazy {
            createPhaseBasedKeyMapAndReturn {
                deloadRoutineIntensityTable.toRoutineIntensity()
            }
        }

        private val deloadRoutineIntensityTable = listOf(
            5 to 0.4, 5 to 0.5, 5 to 0.6
        )
    }
}
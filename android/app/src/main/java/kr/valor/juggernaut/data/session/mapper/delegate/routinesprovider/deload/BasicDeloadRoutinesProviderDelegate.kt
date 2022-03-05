package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.deload

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.createPhaseBasedKeyMapAndReturn
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.PhaseEntireRoutineIntensityTable
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.PhaseRoutineIntensityItem
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensityTableFactory
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.DeloadSession.DeloadSessionRoutine as DeloadSessionRoutine
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.SessionRoutine


class BasicDeloadRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): RoutinesProviderDelegate, RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override fun provideRoutines(phase: Phase, tmWeights: Double): DeloadSessionRoutine {
        val deloadRoutineIntensitiesOfCurrentPhase = entireRoutineIntensityTable[phase]!!
        val routines = deloadRoutineIntensitiesOfCurrentPhase.map { (repetitions, intensityPercentage) ->
            Routine(
                weights = mediate(tmWeights * intensityPercentage),
                reps = repetitions
            )
        }

        return DeloadSessionRoutine(
            routines = routines
        )
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
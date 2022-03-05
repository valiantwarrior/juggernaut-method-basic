package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap.AMRAPRoutinesProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.deload.BasicDeloadRoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.AmrapSession
import kr.valor.juggernaut.domain.session.model.DeloadSession
import kr.valor.juggernaut.domain.session.model.Routine
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import java.lang.IllegalArgumentException


fun RoutinesProviderDelegate.`validate routines that were generated by delegate`(table: PhaseEntireRoutineIntensityTable, action: (Double) -> Double) {
    Phase.values().forEach { phase ->
        val tm = 60.0
        val sessionRoutines = provideRoutines(phase, tm)
        val routines = when(this) {
            is AMRAPRoutinesProviderDelegate -> {
                sessionRoutines as AmrapSession.AmrapSessionRoutine
                with(sessionRoutines) { warmupRoutines + amrapRoutine }
            }
            is BasicDeloadRoutinesProviderDelegate -> {
                (sessionRoutines as DeloadSession.DeloadSessionRoutine).routines
            }
            else -> null
        }

        routines?.let {
            it.forEachIndexed { index, (weights, reps) ->
                val routineIntensityTable = table[phase]!!
                val (repetitions, intensity) = routineIntensityTable[index]

                assertThat(weights, `is`(action(tm * intensity)))
                assertThat(reps, `is`(repetitions))
            }
        } ?: throw IllegalArgumentException()
    }
}
package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.property.DefaultPropertyMediateDelegate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class BasicRealizationRoutinesProviderDelegateTest {

    private lateinit var realizationRoutinesProviderDelegate: AMRAPRoutinesProviderDelegate

    private lateinit var action: (Double) -> Double

    private val tm = 60.0

    @Before
    fun `init`() {
        realizationRoutinesProviderDelegate =
            BasicRealizationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)

        action = DefaultPropertyMediateDelegate::mediate
    }

    @Test
    fun `BasicRealizationRoutinesProviderDelegate generates routines for 'Phase - REP 10' as expected`() {
        `validating routines`(Phase.REP10)
    }

    @Test
    fun `BasicRealizationRoutinesProviderDelegate generates routines for 'Phase - REP 8' as expected`() {
        `validating routines`(Phase.REP8)
    }

    @Test
    fun `BasicRealizationRoutinesProviderDelegate generates routines for 'Phase - REP 5' as expected`() {
        `validating routines`(Phase.REP5)
    }

    @Test
    fun `BasicRealizationRoutinesProviderDelegate generates routines for 'Phase - REP 3' as expected`() {
        `validating routines`(Phase.REP3)
    }

    private fun `validating routines`(phase: Phase) {
        val routines = realizationRoutinesProviderDelegate.provideRoutines(phase, tm)

        val warmupRoutines = routines.warmupRoutines
        val warmupRoutineIntensities =
            BasicRealizationRoutinesProviderDelegate.REALIZATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[phase]!!

        warmupRoutineIntensities.forEachIndexed { index, (repetitions, intensityPercentage) ->
            with(warmupRoutines[index]) {
                assertThat(weights, `is`(action(tm * intensityPercentage)))
                assertThat(reps, `is`(repetitions))
            }
        }

        val amrapRoutine = routines.amrapRoutine
        val (amrapBaseRepetitions, amrapRoutineIntensity) =
            BasicRealizationRoutinesProviderDelegate.REALIZATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[phase]!!

        with(amrapRoutine) {
            assertThat(weights, `is`(action(tm * amrapRoutineIntensity)))
            assertThat(reps, `is`(amrapBaseRepetitions))
        }
    }
}
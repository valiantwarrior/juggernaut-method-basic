package kr.valor.juggernaut.data.session.mapper.delegate.provider

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.DefaultPropertyMediateDelegate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class BasicAccumulationRoutinesProviderDelegateTest {

    private lateinit var accumulationRoutinesProviderDelegate: BasicAccumulationRoutinesProviderDelegate

    @Before
    fun `init`() {
        accumulationRoutinesProviderDelegate =
            BasicAccumulationRoutinesProviderDelegate(
                DefaultPropertyMediateDelegate
            )
    }

    @Test
    fun `BasicAccumulationRoutinesProviderDelegate generates routines of 'Phase - REP 10' as expected`() {
        val tm = 60.0
        val phase = Phase.REP10
        val routines = accumulationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction =  accumulationRoutinesProviderDelegate::mediate

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP10_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP10_INTENSITY)))

        val warmupRoutines = routines.warmupRoutines
        assertThat(warmupRoutines.size, `is`(ACCUMULATION_CYCLE_PHASE_REP10_SETS))
        warmupRoutines.forEach {
            assertThat(it.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP10_REPETITIONS))
            assertThat(it.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP10_INTENSITY)))
        }
    }

    @Test
    fun `BasicAccumulationRoutinesProviderDelegate generates routines of 'Phase - REP 8' as expected`() {
        val tm = 60.0
        val phase = Phase.REP8
        val routines = accumulationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction =  accumulationRoutinesProviderDelegate::mediate

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP8_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP8_INTENSITY)))

        val warmupRoutines = routines.warmupRoutines
        assertThat(warmupRoutines.size, `is`(ACCUMULATION_CYCLE_PHASE_REP8_SETS))
        warmupRoutines.forEach {
            assertThat(it.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP8_REPETITIONS))
            assertThat(it.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP8_INTENSITY)))
        }
    }

    @Test
    fun `BasicAccumulationRoutinesProviderDelegate generates routines of 'Phase - REP 5' as expected`() {
        val tm = 60.0
        val phase = Phase.REP5
        val routines = accumulationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction =  accumulationRoutinesProviderDelegate::mediate

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP5_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP5_INTENSITY)))

        val warmupRoutines = routines.warmupRoutines
        assertThat(warmupRoutines.size, `is`(ACCUMULATION_CYCLE_PHASE_REP5_SETS))
        warmupRoutines.forEach {
            assertThat(it.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP5_REPETITIONS))
            assertThat(it.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP5_INTENSITY)))
        }
    }

    @Test
    fun `BasicAccumulationRoutinesProviderDelegate generates routines of 'Phase - REP 3' as expected`() {
        val tm = 60.0
        val phase = Phase.REP3
        val routines = accumulationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction =  accumulationRoutinesProviderDelegate::mediate

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP3_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP3_INTENSITY)))

        val warmupRoutines = routines.warmupRoutines
        assertThat(warmupRoutines.size, `is`(ACCUMULATION_CYCLE_PHASE_REP3_SETS))
        warmupRoutines.forEach {
            assertThat(it.reps, `is`(ACCUMULATION_CYCLE_PHASE_REP3_REPETITIONS))
            assertThat(it.weights, `is`(mediatorAction(tm * ACCUMULATION_CYCLE_PHASE_REP3_INTENSITY)))
        }
    }

}
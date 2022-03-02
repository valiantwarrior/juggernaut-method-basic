package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.DefaultPropertyMediateDelegate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.number.IsCloseTo

import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BasicIntensificationRoutinesProviderDelegateTest {

    private lateinit var intensificationRoutinesProviderDelegate: BasicIntensificationRoutinesProviderDelegate

    @Before
    fun setUp() {
        intensificationRoutinesProviderDelegate = BasicIntensificationRoutinesProviderDelegate(DefaultPropertyMediateDelegate)
    }

    @Test
    fun `BasicIntensificationRoutinesProviderDelegate generates routines of 'Phase - REP 10' as expected`() {
        val tm = 60.0
        val phase = Phase.REP10
        val routines = intensificationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction = DefaultPropertyMediateDelegate::mediate

        val warmupRoutines = routines.warmupRoutines.subList(0, 2)
        val warmupRoutinesIntensities =
            (INTENSIFICATION_CYCLE_PHASE_REP10_WARMUP_SET1_INTENSITY to INTENSIFICATION_CYCLE_PHASE_REP10_WARMUP_SET2_INTENSITY).toList()
        repeat(2) { warmupSetIndex ->
            with(warmupRoutines[warmupSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP10_WARMUP_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * warmupRoutinesIntensities[warmupSetIndex])))
            }
        }

        val mainRoutines = routines.warmupRoutines.subList(2, routines.warmupRoutines.size)
        val mainRoutinesIntensity = INTENSIFICATION_CYCLE_PHASE_REP10_MAIN_SET_INTENSITY
        repeat(2) { mainSetIndex ->
            with(mainRoutines[mainSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP10_MAIN_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * mainRoutinesIntensity)))
            }
        }

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP10_MAIN_OVERALL_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * INTENSIFICATION_CYCLE_PHASE_REP10_MAIN_SET_INTENSITY)))
    }


    @Test
    fun `BasicIntensificationRoutinesProviderDelegate generates routines of 'Phase - REP 8' as expected`() {
        val tm = 60.0
        val phase = Phase.REP8
        val routines = intensificationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction = DefaultPropertyMediateDelegate::mediate

        val warmupRoutines = routines.warmupRoutines.subList(0, 2)
        val warmupRoutinesIntensities =
            (INTENSIFICATION_CYCLE_PHASE_REP8_WARMUP_SET1_INTENSITY to INTENSIFICATION_CYCLE_PHASE_REP8_WARMUP_SET2_INTENSITY).toList()
        repeat(2) { warmupSetIndex ->
            with(warmupRoutines[warmupSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP8_WARMUP_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * warmupRoutinesIntensities[warmupSetIndex])))
            }
        }

        val mainRoutines = routines.warmupRoutines.subList(2, routines.warmupRoutines.size)
        val mainRoutinesIntensity = INTENSIFICATION_CYCLE_PHASE_REP8_MAIN_SET_INTENSITY
        repeat(2) { mainSetIndex ->
            with(mainRoutines[mainSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP8_MAIN_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * mainRoutinesIntensity)))
            }
        }

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP8_MAIN_OVERALL_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * INTENSIFICATION_CYCLE_PHASE_REP8_MAIN_SET_INTENSITY)))
    }

    @Test
    fun `BasicIntensificationRoutinesProviderDelegate generates routines of 'Phase - REP 5' as expected`() {
        val tm = 60.0
        val phase = Phase.REP5
        val routines = intensificationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction = DefaultPropertyMediateDelegate::mediate

        val warmupRoutines = routines.warmupRoutines.subList(0, 2)
        val warmupRoutinesIntensities =
            (INTENSIFICATION_CYCLE_PHASE_REP5_WARMUP_SET1_INTENSITY to INTENSIFICATION_CYCLE_PHASE_REP5_WARMUP_SET2_INTENSITY).toList()
        repeat(2) { warmupSetIndex ->
            with(warmupRoutines[warmupSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP5_WARMUP_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * warmupRoutinesIntensities[warmupSetIndex])))
            }
        }

        val mainRoutines = routines.warmupRoutines.subList(2, routines.warmupRoutines.size)
        val mainRoutinesIntensity = INTENSIFICATION_CYCLE_PHASE_REP5_MAIN_SET_INTENSITY
        repeat(3) { mainSetIndex ->
            with(mainRoutines[mainSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP5_MAIN_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * mainRoutinesIntensity)))
            }
        }

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP5_MAIN_OVERALL_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * INTENSIFICATION_CYCLE_PHASE_REP5_MAIN_SET_INTENSITY)))
    }

    @Test
    fun `BasicIntensificationRoutinesProviderDelegate generates routines of 'Phase - REP 3' as expected`() {
        val tm = 60.0
        val phase = Phase.REP3
        val routines = intensificationRoutinesProviderDelegate.provideRoutines(phase, tm)
        val mediatorAction = DefaultPropertyMediateDelegate::mediate

        val warmupRoutines = routines.warmupRoutines.subList(0, 2)
        val warmupRoutinesIntensities =
            (INTENSIFICATION_CYCLE_PHASE_REP3_WARMUP_SET1_INTENSITY to INTENSIFICATION_CYCLE_PHASE_REP3_WARMUP_SET2_INTENSITY).toList()
        repeat(2) { warmupSetIndex ->
            with(warmupRoutines[warmupSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP3_WARMUP_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * warmupRoutinesIntensities[warmupSetIndex])))
            }
        }

        val mainRoutines = routines.warmupRoutines.subList(2, routines.warmupRoutines.size)
        val mainRoutinesIntensity = INTENSIFICATION_CYCLE_PHASE_REP3_MAIN_SET_INTENSITY
        repeat(4) { mainSetIndex ->
            with(mainRoutines[mainSetIndex]) {
                assertThat(reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP3_MAIN_OVERALL_REPETITIONS))
                assertThat(weights, `is`(mediatorAction(tm * mainRoutinesIntensity)))
            }
        }

        val amrapRoutine = routines.amrapRoutine
        assertThat(amrapRoutine.reps, `is`(INTENSIFICATION_CYCLE_PHASE_REP3_MAIN_OVERALL_REPETITIONS))
        assertThat(amrapRoutine.weights, `is`(mediatorAction(tm * INTENSIFICATION_CYCLE_PHASE_REP3_MAIN_SET_INTENSITY)))
    }

    @Test
    fun `simple`() {
        assertThat(42.0, `is`(60.0 * 0.7))
    }
}
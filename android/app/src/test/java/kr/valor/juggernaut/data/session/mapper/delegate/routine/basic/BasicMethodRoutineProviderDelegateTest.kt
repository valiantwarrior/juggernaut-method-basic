package kr.valor.juggernaut.data.session.mapper.delegate.routine.basic

import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression
import org.junit.Before
import org.junit.Test


class BasicMethodRoutineProviderDelegateTest {

    private lateinit var target: RoutineProviderDelegate<Progression>

    private lateinit var routineIntensitySource: RoutineIntensitySource<MicroCycle, Phase>

    private lateinit var action: (Double) -> Double

    @Before
    fun `init`() {
        routineIntensitySource = TestServiceLocator.provideRoutineIntensitySource()
        target = TestServiceLocator.provideRoutineProviderDelegate()
        action = TestServiceLocator.provideRoutinePropertyMediateDelegate()::mediate
    }

    @Test
    fun `validate routines`() {
        val tm = 60.0

        Phase.values().forEach { phase ->
            MicroCycle.values().forEach { microCycle ->
                val progression = Progression(phase, microCycle)
                val routineIntensity =
                    routineIntensitySource.provideRoutineIntensityMap(microCycle)

                val expectedRoutines = routineIntensity[phase]!!.toRoutineModels(tm, action)

                val actualRoutines = target.provideSessionRoutine(progression, tm)

                actualRoutines.forEachIndexed { index, actual ->
                    assertThat(actual.weights, `is`(expectedRoutines[index].weights))
                    assertThat(actual.reps, `is`(expectedRoutines[index].reps))
                }
            }
        }
    }
}
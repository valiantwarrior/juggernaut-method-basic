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

    private lateinit var action: (Double) -> Int

    @Before
    fun `init`() {
        routineIntensitySource = TestServiceLocator.provideRoutineIntensitySource()
        target = TestServiceLocator.provideRoutineProviderDelegate()
        action = TestServiceLocator.provideRoutinePropertyMediateDelegate()::convert
    }

    @Test
    fun `validate routines when actualRepetitions is null`() {
        val tm = 60

        Phase.values().forEach { phase ->
            MicroCycle.values().forEach { microCycle ->
                val progression = Progression(phase, microCycle)
                val routineIntensity =
                    routineIntensitySource.provideRoutineIntensityMap(microCycle)

                val expectedRoutines = routineIntensity[phase]!!.map {
                    it.toRoutineModel(tmWeights = tm, transform = action)
                }

                val actualRoutines = target.provideSessionRoutine(progression, tm, null)

                actualRoutines.forEachIndexed { index, actual ->
                    assertThat(actual.weights, `is`(expectedRoutines[index].weights))
                    assertThat(actual.reps, `is`(expectedRoutines[index].reps))
                }
            }
        }
    }

    @Test
    fun `validate routines when actualRepetitions is not null`() {
        val tm = 60
        val amrapMap = mapOf(
            Phase.REP10 to 16,
            Phase.REP8 to 12,
            Phase.REP5 to 7,
            Phase.REP3 to 5
        )

        Phase.values().forEach { phase ->
            val actualReps = amrapMap[phase]!!
            MicroCycle.values().forEach { microCycle ->
                val progression = Progression(phase, microCycle)
                val routineIntensity =
                    routineIntensitySource.provideRoutineIntensityMap(microCycle)

                val expectedRoutines = if (microCycle == MicroCycle.DELOAD) {
                    routineIntensity[phase]!!.map {
                        it.toRoutineModel(
                            tmWeights = tm,
                            transform = action
                        )
                    }
                } else {
                    val warmupRoutines = routineIntensity[phase]!!.dropLast(1).map {
                        it.toRoutineModel(
                            tmWeights = tm,
                            transform = action
                        )
                    }
                    val amrapRoutine = routineIntensity[phase]!!.last().toRoutineModel(
                        tmWeights = tm,
                        actualRepetitions = actualReps,
                        transform = action
                    )

                    warmupRoutines + amrapRoutine
                }

                val actualRoutines = target.provideSessionRoutine(progression ,tm, actualReps)

                actualRoutines.forEachIndexed { index, actual ->
                    assertThat(actual.weights, `is`(expectedRoutines[index].weights))
                    assertThat(actual.reps, `is`(expectedRoutines[index].reps))
                }
            }
        }
    }
}
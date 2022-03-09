package kr.valor.juggernaut.domain.session.model

import kr.valor.juggernaut.TestServiceLocator
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class RoutineIntensityKtTest {

    private lateinit var routineIntensity: RoutineIntensity

    private lateinit var mediate: (Double) -> Int

    @Before
    fun `init`() {
        routineIntensity = RoutineIntensity(
            repetitions = 10,
            intensityPercentage = 0.6
        )

        mediate = TestServiceLocator.provideRoutinePropertyMediateDelegate()::mediate
    }

    @Test
    fun `validate routine model mapping function when actualRepetitions is null`() {
        val tm = 60
        val routine = routineIntensity.toRoutineModel(tmWeights = 60, transform = mediate)

        assertThat(routine.weights, `is`(mediate(tm * routineIntensity.intensityPercentage)))
        assertThat(routine.reps, `is`(routineIntensity.repetitions))
    }

    @Test
    fun `validate routine model mapping function when actualRepetitions is not null`() {
        val tm = 60
        val actualRepetitions = 16
        val routine = routineIntensity.toRoutineModel(tmWeights = 60, actualRepetitions = actualRepetitions, transform = mediate)

        assertThat(routine.weights, `is`(mediate(tm * routineIntensity.intensityPercentage)))
        assertThat(routine.reps, `is`(actualRepetitions))
    }

}
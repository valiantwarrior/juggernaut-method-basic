package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.converter.KgWeightUnitTransformer
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.domain.session.model.SessionProgression
import kr.valor.juggernaut.domain.session.model.toRoutineModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class AmrapRoutineProviderDelegateTest {

    private val routineIntensitySource = InMemoryRoutineIntensitySource()

    private val weightUnitTransformer = KgWeightUnitTransformer()

    private val routineProvider = AmrapRoutineProviderDelegate(
        routineIntensitySource, weightUnitTransformer
    )

    @Test
    fun `amrapRoutineProvider provide AMRAP routine as expected when AMRAP session`() {
        val sessionProgression = SessionProgression(MethodCycle.INITIAL, Phase.INITIAL, MicroCycle.INITIAL)

        val expectedRoutineIntensity =
            routineIntensitySource.provideRoutineIntensityMap(sessionProgression.microCycle)[sessionProgression.phase]!!.last()
        var expectedAmrapRoutine =
            expectedRoutineIntensity.toRoutineModel(60, null, weightUnitTransformer::transform)

        var actualAmrapRoutine = routineProvider.provideSessionRoutine(sessionProgression, 60, null)

        assertThat(actualAmrapRoutine, notNullValue())
        assertThat(actualAmrapRoutine!!.reps, `is`(expectedAmrapRoutine.reps))
        assertThat(actualAmrapRoutine!!.weights, `is`(expectedAmrapRoutine.weights))
        assertThat(actualAmrapRoutine!!.baseIntensity, `is`(expectedRoutineIntensity))


        expectedAmrapRoutine =
            expectedRoutineIntensity.toRoutineModel(60, 14, weightUnitTransformer::transform)

        actualAmrapRoutine = routineProvider.provideSessionRoutine(sessionProgression, 60, 14)

        assertThat(actualAmrapRoutine, notNullValue())
        assertThat(actualAmrapRoutine!!.reps, `is`(expectedAmrapRoutine.reps))
        assertThat(actualAmrapRoutine!!.weights, `is`(expectedAmrapRoutine.weights))
        assertThat(actualAmrapRoutine!!.baseIntensity, `is`(expectedRoutineIntensity))

    }

    @Test
    fun `amrapRoutineProvider return null when DELOAD session `() {
        val sessionProgression = SessionProgression(MethodCycle.INITIAL, Phase.INITIAL, MicroCycle.DELOAD)

        val actualAmrapRoutine = routineProvider.provideSessionRoutine(sessionProgression, 60, null)
        assertThat(actualAmrapRoutine, nullValue())
    }

}
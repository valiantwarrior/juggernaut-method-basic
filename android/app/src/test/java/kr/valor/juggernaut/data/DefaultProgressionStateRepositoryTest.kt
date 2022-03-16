package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class DefaultProgressionStateRepositoryTest {
    private lateinit var repository: ProgressionStateRepository

    @Before
    fun `init`() {
        repository = TestServiceLocator.progressionStateRepository
    }

    @After
    fun `nuke repo`() = runBlocking {
        repository.clear()
    }

    @Test
    fun `getProgressionState() returns ProgressionStateNone as expected`() = runTest{
        val progressionState = repository.getProgressionState().first()
        assertThat(progressionState, `is`(ProgressionState.None))
    }

    @Test
    fun `updateMethodProgressState() works as expected`() = runTest {
        repository.updateMethodProgressState(MethodProgressState.NONE)
        var progressionState = repository.getProgressionState().first()
        assertThat(progressionState, `is`(ProgressionState.None))

        repository.updateMethodProgressState(MethodProgressState.ONGOING)
        progressionState = repository.getProgressionState().first()
        assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))
        progressionState as ProgressionState.OnGoing
        assertThat(progressionState.currentUserProgression.phase, `is`(Phase.INITIAL))
        assertThat(progressionState.currentUserProgression.microCycle, `is`(MicroCycle.INITIAL))
        assertThat(progressionState.currentUserProgression.methodCycle.value, `is`(MethodCycle.INITIAL_VALUE))
    }

    @Test
    fun `updateUseProgression() works as expected`() = runTest {
        val updateValue = MicroCycle.INTENSIFICATION
        repository.updateMethodProgressState(MethodProgressState.ONGOING)
        repository.updateMicroCycleState(updateValue)

        val progressionState = repository.getProgressionState().first()
        val userProgression = (progressionState as ProgressionState.OnGoing).currentUserProgression

        assertThat(userProgression.methodCycle.value, `is`(MethodCycle.INITIAL_VALUE))
        assertThat(userProgression.phase, `is`(Phase.INITIAL))
        assertThat(userProgression.microCycle, `is`(updateValue))
    }

}
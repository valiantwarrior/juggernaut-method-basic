package kr.valor.juggernaut.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.di.DataStoreScope
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import kr.valor.juggernaut.runTestAndCleanup
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DefaultSessionProgressionStateRepositoryAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: ProgressionStateRepository

    @DataStoreScope
    @Inject
    lateinit var testScope: TestScope

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun givenNoneInitProgressionState_getProgressionState_returnsProgressionStateNone() {
        testScope.runTestAndCleanup(repository::clear) {
            val progressionState = repository.getProgressionState().first()
            assertThat(progressionState, instanceOf(ProgressionState.None::class.java))
        }
    }

    @Test
    fun initProgressionState_getProgressionState_returnsExpectedResult() {
        testScope.runTestAndCleanup(repository::clear) {
            repository.updateMethodProgressState(MethodProgressState.ONGOING)
            val progressionState = repository.getProgressionState().first()

            assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))
            progressionState as ProgressionState.OnGoing
            val userProgression = progressionState.currentUserProgression
            assertThat(userProgression.methodCycle, `is`(MethodCycle.INITIAL))
            assertThat(userProgression.phase, `is`(Phase.INITIAL))
            assertThat(userProgression.microCycle, `is`(MicroCycle.INITIAL))
        }
    }

    @Test
    fun updateProgressionState_worksAsExpected() {
        testScope.runTestAndCleanup(repository::clear) {
            repository.updateMethodProgressState(MethodProgressState.ONGOING)
            var progressionState = repository.getProgressionState().first()
            assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))

            repository.updatePhaseState(Phase.REP8)
            var userProgression = (repository.getProgressionState().first() as ProgressionState.OnGoing).currentUserProgression
            assertThat(userProgression.microCycle, `is`(MicroCycle.INITIAL))
            assertThat(userProgression.phase, `is`(Phase.REP8))
            assertThat(userProgression.methodCycle, `is`(MethodCycle.INITIAL))

            repository.updateMicroCycleState(MicroCycle.INTENSIFICATION)
            userProgression = (repository.getProgressionState().first() as ProgressionState.OnGoing).currentUserProgression
            assertThat(userProgression.microCycle, `is`(MicroCycle.INTENSIFICATION))
            assertThat(userProgression.phase, `is`(Phase.REP8))
            assertThat(userProgression.methodCycle, `is`(MethodCycle.INITIAL))


            repository.updateMethodCycleState(MethodCycle(2))
            userProgression = (repository.getProgressionState().first() as ProgressionState.OnGoing).currentUserProgression
            assertThat(userProgression.microCycle, `is`(MicroCycle.INTENSIFICATION))
            assertThat(userProgression.phase, `is`(Phase.REP8))
            assertThat(userProgression.methodCycle, `is`(MethodCycle(2)))

            progressionState = repository.getProgressionState().first()
            assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))

            progressionState as ProgressionState.OnGoing
            userProgression = progressionState.currentUserProgression

            assertThat(userProgression.methodCycle, `is`(MethodCycle(2)))
            assertThat(userProgression.phase, `is`(Phase.REP8))
            assertThat(userProgression.microCycle, `is`(MicroCycle.INTENSIFICATION))
        }
    }

    @Test
    fun updateMethodProgressState_toNone_worksAsExpected() {
        testScope.runTestAndCleanup(repository::clear) {
            var progressionState = repository.getProgressionState().first()
            assertThat(progressionState, instanceOf(ProgressionState.None::class.java))

            repository.updateMethodProgressState(MethodProgressState.ONGOING)
            progressionState = repository.getProgressionState().first()
            assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))

            repository.updateMethodProgressState(MethodProgressState.NONE)
            progressionState = repository.getProgressionState().first()
            assertThat(progressionState, not(instanceOf(ProgressionState.None::class.java)))
            assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))
        }
    }

}
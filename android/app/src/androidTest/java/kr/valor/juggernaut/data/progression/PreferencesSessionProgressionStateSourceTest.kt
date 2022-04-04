package kr.valor.juggernaut.data.progression

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.di.DataStoreScope
import kr.valor.juggernaut.data.progression.di.DataStoreSource
import kr.valor.juggernaut.data.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.runTestAndCleanup
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PreferencesSessionProgressionStateSourceTest {

    @get:Rule
    var rule = HiltAndroidRule(this)

    @DataStoreSource
    @Inject
    lateinit var progressionStateDataSource: ProgressionStateDataSource

    @DataStoreScope
    @Inject
    lateinit var testScope: TestScope

    @Before
    fun init() {
        rule.inject()
    }

    @Test
    fun givenEmptyPreferences_getProgressionStateData_returns_None() {
        testScope.runTestAndCleanup(progressionStateDataSource::clear) {
            val progressionState = progressionStateDataSource.getProgressionStateData().first()
            assertThat(progressionState, instanceOf(ProgressionState.None::class.java))
        }
    }

    @Test
    fun givenEmptyPreferences_editProgressionState_worksAsExpected(){
        testScope.runTestAndCleanup(progressionStateDataSource::clear) {
            var progressionState = progressionStateDataSource.getProgressionStateData().first()
            assertThat(progressionState, instanceOf(ProgressionState.None::class.java))

            progressionStateDataSource.editMethodProgressState(MethodProgressState.ONGOING)
            progressionState = progressionStateDataSource.getProgressionStateData().first()
            assertThat(progressionState, instanceOf(ProgressionState.OnGoing::class.java))

            progressionState as ProgressionState.OnGoing
            val userProgression = progressionState.currentUserProgression
            assertThat(userProgression.methodCycle, `is`(MethodCycle.INITIAL))
            assertThat(userProgression.phase, `is`(Phase.INITIAL))
            assertThat(userProgression.microCycle, `is`(MicroCycle.INITIAL))
        }
    }
}
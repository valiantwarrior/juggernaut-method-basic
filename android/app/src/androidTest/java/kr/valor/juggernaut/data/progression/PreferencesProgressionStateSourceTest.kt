package kr.valor.juggernaut.data.progression

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MethodProgressState
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.progression.source.ProgressionStateDataSource
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PreferencesProgressionStateSourceTest {

    @get:Rule
    var rule = HiltAndroidRule(this)

    @Inject lateinit var progressionStateDataSource: ProgressionStateDataSource

    private val testCoroutineScope = TestScope()

    @Before
    fun init() {
        rule.inject()
    }

    @After
    fun cleanUp() {
        testCoroutineScope.launch {
            progressionStateDataSource.clear()
        }
        testCoroutineScope.cancel()
    }

    @Test
    fun givenEmptyPreferences_getProgressionStateData_returns_None() {
        testCoroutineScope.launch {
            val progressionState = progressionStateDataSource.getProgressionStateData().first()
            assertThat(progressionState, instanceOf(ProgressionState.None::class.java))
        }
    }

    @Test
    fun givenEmptyPreferences_editProgressionState_worksAsExpected(){
        testCoroutineScope.launch {
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
package kr.valor.juggernaut.domain.common

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kr.valor.juggernaut.TestData
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.di.DataStoreScope
import kr.valor.juggernaut.domain.TestClearUseCase
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.runTestAndCleanup
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class StartMethodSetupContractTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var startMethodSetupContract: StartMethodSetupContract

    @Inject
    lateinit var loadProgressionStateUseCase: LoadProgressionStateUseCase

    @Inject
    lateinit var clear: TestClearUseCase

    @DataStoreScope
    @Inject
    lateinit var testScope: TestScope

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun givenProgressionStateNone_startMethodSetupContract_worksAsExpected() {
        testScope.runTestAndCleanup(clear::invoke) {
            var currentProgressionState = loadProgressionStateUseCase().first()
            assertThat(currentProgressionState, instanceOf(ProgressionState.None::class.java))

            startMethodSetupContract(TestData.liftCategoryWeightsMap)

            currentProgressionState = loadProgressionStateUseCase().first()
            assertThat(currentProgressionState, instanceOf(ProgressionState.OnGoing::class.java))

            val currentUserProgression = (currentProgressionState as ProgressionState.OnGoing).currentUserProgression
            assertThat(currentUserProgression.methodCycle, `is`(MethodCycle.INITIAL))
            assertThat(currentUserProgression.phase, `is`(Phase.INITIAL))
            assertThat(currentUserProgression.microCycle, `is`(MicroCycle.INITIAL))
        }
    }


}
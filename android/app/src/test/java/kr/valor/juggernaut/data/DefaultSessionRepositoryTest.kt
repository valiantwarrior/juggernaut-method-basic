package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test


class DefaultSessionRepositoryTest {

    private lateinit var repository: SessionRepository

    @Before
    fun `init`() {
        repository = TestServiceLocator.sessionRepository
    }

    @After
    fun `nuke session`() = runBlocking {
        repository.clear()
    }

    @Test
    fun `repository synchronizeSession() works as expected`() = runBlocking {
        repository.getAllSessions().collect {
            assertThat(it.isEmpty(), `is`(true))
        }

        var userProgression = UserProgression(
            1, Phase.REP10, MicroCycle.ACCUMULATION, LiftCategory.BENCH_PRESS
        )
        var userTrainingMax = UserTrainingMax(
            1L, LiftCategory.BENCH_PRESS, 60, System.currentTimeMillis()
        )

        repository.synchronizeSession(userProgression, userTrainingMax)
        repository.getSession().first().let {
            assertThat(it, `is`(notNullValue()))
            assertThat(it, instanceOf(Session::class.java))
            assertThat(it.progression.phase, `is`(Phase.REP10))
            assertThat(it.progression.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.category, `is`(LiftCategory.BENCH_PRESS))
            assertThat(it.tmWeights, `is`(60))
        }
        repository.getAllSessions().first().let {
            assertThat(it.size, `is`(1))
        }

        repository.synchronizeSession(userProgression, userTrainingMax)
        repository.getSession().first().let {
            assertThat(it, `is`(notNullValue()))
            assertThat(it, instanceOf(Session::class.java))
            assertThat(it.progression.phase, `is`(Phase.REP10))
            assertThat(it.progression.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.category, `is`(LiftCategory.BENCH_PRESS))
            assertThat(it.tmWeights, `is`(60))
        }
        repository.getAllSessions().first().let {
            assertThat(it.size, `is`(1))
        }

        userProgression = userProgression.copy(liftCategory = LiftCategory.DEAD_LIFT)
        userTrainingMax = userTrainingMax.copy(liftCategory = LiftCategory.DEAD_LIFT, trainingMaxWeights = 100)

        repository.synchronizeSession(userProgression, userTrainingMax)
        repository.getSession().first().let {
            assertThat(it, `is`(notNullValue()))
            assertThat(it, instanceOf(Session::class.java))
            assertThat(it.progression.phase, `is`(Phase.REP10))
            assertThat(it.progression.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.category, `is`(LiftCategory.DEAD_LIFT))
            assertThat(it.tmWeights, `is`(100))
        }
        repository.getAllSessions().first().let {
            assertThat(it.size, `is`(2))
        }
    }
}
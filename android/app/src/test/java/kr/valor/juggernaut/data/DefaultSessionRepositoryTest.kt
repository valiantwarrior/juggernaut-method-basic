package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test


class DefaultSessionRepositoryTest {

    private lateinit var repository: SessionRepository
    private lateinit var mockUserProgression: UserProgression
    private lateinit var mockUserTrainingMaxes: List<UserTrainingMax>

    @Before
    fun `init`() {
        repository = TestServiceLocator.sessionRepository
        initMock()
    }

    @After
    fun `nuke session`() = runBlocking {
        repository.clear()
    }

    @Test
    fun `synchronizeSessions() works as expected`() = runTest {
        val initialSessions = repository.findSessionsByUserProgression(mockUserProgression).single()
        assertThat(initialSessions.size, `is`(0))

        repository.synchronizeSessions(mockUserProgression, mockUserTrainingMaxes)
        var synchronizedSessions = repository.findSessionsByUserProgression(mockUserProgression).single()
        assertThat(synchronizedSessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
        synchronizedSessions.forEach { session ->
            assertThat(session, instanceOf(Session::class.java))
            val (methodCycle, phase, microCycle) = session.progression
            with(mockUserProgression) {
                assertThat(methodCycle, `is`(this.methodCycle))
                assertThat(phase, `is`(this.phase))
                assertThat(microCycle, `is`(this.microCycle))
            }
        }

        synchronizedSessions = repository.getAllSessions().single()
        assertThat(synchronizedSessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))

        LiftCategory.values().forEach { liftCategory ->
            var session = synchronizedSessions.find { it.category == liftCategory }!!
            val trainingMax = mockUserTrainingMaxes.find { it.liftCategory == liftCategory }!!
            assertThat(session.tmWeights, `is`(trainingMax.trainingMaxWeights))
            assertThat(session.category, `is`(trainingMax.liftCategory))
        }

        mockUserProgression = mockUserProgression.copy(microCycle = MicroCycle.INTENSIFICATION)
        repository.synchronizeSessions(mockUserProgression, mockUserTrainingMaxes)
        synchronizedSessions = repository.findSessionsByUserProgression(mockUserProgression).single()
        assertThat(synchronizedSessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
        synchronizedSessions.forEach { session ->
            val (methodCycle, phase, microCycle) = session.progression
            with(mockUserProgression) {
                assertThat(methodCycle, `is`(this.methodCycle))
                assertThat(phase, `is`(this.phase))
                assertThat(microCycle, `is`(this.microCycle))
            }
        }

        synchronizedSessions = repository.getAllSessions().single()
        assertThat(synchronizedSessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT * 2))
    }

    private fun initMock() {
        mockUserProgression = UserProgression(
            MethodCycle(1), Phase.REP10, MicroCycle.ACCUMULATION
        )

        mockUserTrainingMaxes = listOf(
            UserTrainingMax(
                id = 0L,
                methodCycle = mockUserProgression.methodCycle,
                phase = mockUserProgression.phase,
                liftCategory = LiftCategory.BENCHPRESS,
                trainingMaxWeights = 60,
                lastUpdatedAt = System.currentTimeMillis()
            ),
            UserTrainingMax(
                id = 1L,
                methodCycle = mockUserProgression.methodCycle,
                phase = mockUserProgression.phase,
                liftCategory = LiftCategory.SQUAT,
                trainingMaxWeights = 100,
                lastUpdatedAt = System.currentTimeMillis()
            ),
            UserTrainingMax(
                id = 2L,
                methodCycle = mockUserProgression.methodCycle,
                phase = mockUserProgression.phase,
                liftCategory = LiftCategory.OVERHEADPRESS,
                trainingMaxWeights = 40,
                lastUpdatedAt = System.currentTimeMillis()
            ),
            UserTrainingMax(
                id = 3L,
                methodCycle = mockUserProgression.methodCycle,
                phase = mockUserProgression.phase,
                liftCategory = LiftCategory.DEADLIFT,
                trainingMaxWeights = 140,
                lastUpdatedAt = System.currentTimeMillis()
            )
        )
    }

}
package kr.valor.juggernaut.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DefaultSessionRepositoryAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: SessionRepository

    private val methodCycle = MethodCycle.INITIAL
    private val phase = Phase.INITIAL
    private val microCycle = MicroCycle.INITIAL
    private val userProgression = UserProgression(methodCycle, phase, microCycle)
    private val userProgressionDeload = UserProgression(methodCycle, phase, MicroCycle.DELOAD)

    private val trainingMax = TrainingMax(
        methodCycle = userProgression.methodCycle,
        phase = userProgression.phase,
        liftCategory = LiftCategory.BENCHPRESS,
        trainingMaxWeights = 60,
        lastUpdatedAt = System.currentTimeMillis()
    )
    private val trainingMaxes = listOf(
        trainingMax,
        trainingMax.copy(liftCategory = LiftCategory.SQUAT, trainingMaxWeights = 100),
        trainingMax.copy(liftCategory = LiftCategory.OVERHEADPRESS, trainingMaxWeights = 50),
        trainingMax.copy(liftCategory = LiftCategory.DEADLIFT, trainingMaxWeights = 120)
    )


    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun cleanUp() {
        runTest { repository.clear() }
    }

    @Test
    fun givenEmptySessions_getAllSessions_returnsEmptyList() = runTest {
        val sessions = repository.getAllSessions().first()

        assertThat(sessions.isEmpty(), `is`(true))
    }

    @Test
    fun givenEmptySessions_findSessionsByUserProgression_returnsEmptyList() = runTest {
        val sessions = repository.findSessionsByUserProgression(
            UserProgression(MethodCycle.INITIAL, Phase.INITIAL, MicroCycle.INITIAL)
        ).first()

        assertThat(sessions.isEmpty(), `is`(true))
    }

    @Test
    fun givenSessionsWithUserProgressionINITIAL_findSessionsByUserProgression_returnsAsExpected() = runTest {
        repository.synchronizeSessions(userProgression, trainingMaxes)

        val sessions = repository.findSessionsByUserProgression(userProgression).first()
        assertThat(sessions.isNotEmpty(), `is`(true))
        assertThat(sessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))

        sessions.forEach { session ->
            when(session.category) {
                LiftCategory.BENCHPRESS -> assertThat(session.tmWeights, `is`(60))
                LiftCategory.DEADLIFT -> assertThat(session.tmWeights, `is`(120))
                LiftCategory.OVERHEADPRESS -> assertThat(session.tmWeights, `is`(50))
                LiftCategory.SQUAT -> assertThat(session.tmWeights, `is`(100))
            }
            assertThat(session.progression.methodCycle, `is`(userProgression.methodCycle))
            assertThat(session.progression.phase, `is`(userProgression.phase))
            assertThat(session.progression.microCycle, `is`(userProgression.microCycle))
            assertThat(session.warmupRoutines, `is`(notNullValue()))
            assertThat(session.amrapRoutine, `is`(notNullValue()))
            assertThat(session.deloadRoutines, `is`(nullValue()))
        }
    }

    @Test
    fun givenSessions_deleteSessionsByMethodCycle_worksAsExpected() = runTest {
        repository.synchronizeSessions(userProgression, trainingMaxes)
        val methodCycleOther = MethodCycle(2)
        val userProgressionOther = userProgression.copy(methodCycle = methodCycleOther)
        repository.synchronizeSessions(userProgressionOther, trainingMaxes)

        var sessions = repository.getAllSessions().first()
        assertThat(sessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT * 2))

        repository.deleteSessionsByMethodCycle(methodCycleOther)
        sessions = repository.getAllSessions().first()
        assertThat(sessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))

        sessions = repository.findSessionsByUserProgression(userProgressionOther).first()
        assertThat(sessions.isEmpty(), `is`(true))

        sessions = repository.findSessionsByUserProgression(userProgression).first()
        assertThat(sessions.isNotEmpty(), `is`(true))
        assertThat(sessions.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
    }

    @Test
    fun givenSessions_findSessionById_returnsCorrespondingSession() = runTest {
        repository.synchronizeSessions(userProgression, trainingMaxes)
        val sessions = repository.getAllSessions().first()
        val sessionIds = sessions.mapIndexed { index, session -> index to session.sessionId }

        sessionIds.forEach { (sessionIndex, sessionId) ->
            val sessionFromRepository = repository.findSessionByIdOneShot(sessionId)
            assertThat(sessionFromRepository, `is`(sessions[sessionIndex]))
        }
    }

}
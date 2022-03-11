package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax
import kr.valor.juggernaut.domain.user.repository.UserRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class DefaultUserRepositoryTest {
    private lateinit var repository: UserRepository

    @Before
    fun `init`() {
        repository = TestServiceLocator.provideUserRepository()
    }

    @Test
    fun `getUserTrainingMax works as expected`() = runBlocking {
        val userTrainingMax = repository.getUserTrainingMax(LiftCategory.BENCH_PRESS)
        assertThat(userTrainingMax, instanceOf(UserTrainingMax::class.java))
        assertThat(userTrainingMax.liftCategory, `is`(LiftCategory.BENCH_PRESS))
        assertThat(userTrainingMax.trainingMaxWeights, `is`(60))
    }

    @Test
    fun `create, insert and get tm works as expected`() = runBlocking {
        val liftCategory = LiftCategory.BENCH_PRESS
        val tmWeights = 80

        val currentModel = repository.getUserTrainingMax(liftCategory)

        val newEntity = repository.createUserTrainingMaxEntity(tmWeights, liftCategory)
        repository.insertUserTrainingMaxEntity(newEntity)
        val model = repository.getUserTrainingMax(liftCategory)

        assertThat(model, instanceOf(UserTrainingMax::class.java))
        assertThat(model.trainingMaxWeights, `is`(tmWeights))
        assertThat(model.liftCategory, `is`(liftCategory))
        assertThat(model.lastUpdatedAt > currentModel.lastUpdatedAt, `is`(true))
    }

    @Test
    fun `getUserProgression works as expected`() = runTest {
        repository.getUserProgression().first().let {
            assertThat(it.liftCategory, `is`(LiftCategory.BENCH_PRESS))
            assertThat(it.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.methodCycle, `is`(1))
            assertThat(it.phase, `is`(Phase.REP10))
        }
    }

    @Test
    fun `editUserProgression works as expected`() = runTest {
        repository.getUserProgression().first().let {
            assertThat(it.phase, `is`(Phase.REP10))
            assertThat(it.methodCycle, `is`(1))
            assertThat(it.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.liftCategory, `is`(LiftCategory.BENCH_PRESS))
        }

        repository.updateUserProgression(2)
        repository.getUserProgression().first().let {
            assertThat(it.phase, `is`(Phase.REP10))
            assertThat(it.methodCycle, `is`(2))
            assertThat(it.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.liftCategory, `is`(LiftCategory.BENCH_PRESS))
        }

        repository.updateUserProgression(LiftCategory.DEAD_LIFT)
        repository.getUserProgression().first().let {
            assertThat(it.phase, `is`(Phase.REP10))
            assertThat(it.methodCycle, `is`(2))
            assertThat(it.microCycle, `is`(MicroCycle.ACCUMULATION))
            assertThat(it.liftCategory, `is`(LiftCategory.DEAD_LIFT))
        }
    }

}
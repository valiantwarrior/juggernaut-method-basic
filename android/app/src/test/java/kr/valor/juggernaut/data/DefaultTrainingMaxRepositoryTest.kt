package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class DefaultTrainingMaxRepositoryTest {

    private lateinit var repository: TrainingMaxRepository

    private lateinit var userProgression: UserProgression

    private val transform: (Double) -> Int = TestServiceLocator.provideWeightUnitTransformer()::transform

    private val tmMap = mapOf(
        LiftCategory.BENCHPRESS to 100.0,
        LiftCategory.SQUAT to 140.0,
        LiftCategory.OVERHEADPRESS to 80.0,
        LiftCategory.DEADLIFT to 180.0
    )

    @Before
    fun `init`() {
        repository = TestServiceLocator.userTrainingMaxRepository
        userProgression = UserProgression(
            MethodCycle(1), Phase.REP10, MicroCycle.ACCUMULATION
        )
    }

//    @Test
//    fun `Task - create, insert and get TM - works as expected`() = runTest {
//
//        var initialUserTrainingMaxes =
//            repository.findTrainingMaxesByUserProgression(userProgression)
//        assertThat(initialUserTrainingMaxes.size, `is`(0))
//
//        initialUserTrainingMaxes =
//            repository.getAllTrainingMaxes().first()
//        assertThat(initialUserTrainingMaxes.size, `is`(0))
//
//
//        tmMap.forEach { (liftCategory, weights) ->
//            val userTrainingMax =
//                repository.createTrainingMax(liftCategory, weights, userProgression)
//            repository.insertTrainingMax(userTrainingMax)
//        }
//
//        var afterUserTrainingMaxes =
//            repository.findTrainingMaxesByUserProgression(userProgression)
//
//        assertThat(afterUserTrainingMaxes.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
//        afterUserTrainingMaxes.forEach { userTrainingMax ->
//            with(userTrainingMax) {
//                assertThat(trainingMaxWeights, `is`(transform(tmMap[liftCategory]!!)))
//                assertThat(phase, `is`(userProgression.phase))
//                assertThat(methodCycle, `is`(userProgression.methodCycle))
//            }
//        }
//    }


}
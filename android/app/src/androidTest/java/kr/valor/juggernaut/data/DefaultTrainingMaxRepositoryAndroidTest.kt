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
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import org.hamcrest.CoreMatchers.`is`
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
class DefaultTrainingMaxRepositoryAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: TrainingMaxRepository

    private val userProgression = UserProgression(MethodCycle.INITIAL, Phase.INITIAL, MicroCycle.INITIAL)

    private val liftCategoryWeightsMap = mapOf(
        LiftCategory.BENCHPRESS to 91.5,
        LiftCategory.SQUAT to 140.5,
        LiftCategory.OVERHEADPRESS to 63.1,
        LiftCategory.DEADLIFT to 185.5
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
    fun givenEmptyTrainingMaxes_getAllTrainingMaxes_returnsEmptyList() = runTest {
        val trainingMaxes = repository.getAllTrainingMaxes().first()
        assertThat(trainingMaxes.isEmpty(), `is`(true))
    }

    @Test
    fun createInsertFindAndGetAll_worksAsExpected() = runTest {
        liftCategoryWeightsMap.forEach { (liftCategory, weights) ->
            val trainingMax = repository.createTrainingMax(liftCategory, weights, userProgression)
            repository.insertTrainingMax(trainingMax)
        }

        var trainingMaxes = repository.getAllTrainingMaxes().first()
        assertThat(trainingMaxes.isNotEmpty(), `is`(true))
        assertThat(trainingMaxes.size   , `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
        trainingMaxes.forEach { trainingMax ->
            when(trainingMax.liftCategory) {
                LiftCategory.BENCHPRESS -> assertThat(trainingMax.trainingMaxWeights, `is`(92))
                LiftCategory.SQUAT -> assertThat(trainingMax.trainingMaxWeights, `is`(142))
                LiftCategory.OVERHEADPRESS -> assertThat(trainingMax.trainingMaxWeights, `is`(64))
                LiftCategory.DEADLIFT -> assertThat(trainingMax.trainingMaxWeights, `is`(186))
            }
            assertThat(trainingMax.methodCycle, `is`(userProgression.methodCycle))
            assertThat(trainingMax.phase, `is`(userProgression.phase))
        }

        trainingMaxes = repository.findTrainingMaxesByUserProgression(userProgression)
        assertThat(trainingMaxes.isNotEmpty(), `is`(true))
        assertThat(trainingMaxes.size   , `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
        trainingMaxes.forEach { trainingMax ->
            when(trainingMax.liftCategory) {
                LiftCategory.BENCHPRESS -> assertThat(trainingMax.trainingMaxWeights, `is`(92))
                LiftCategory.SQUAT -> assertThat(trainingMax.trainingMaxWeights, `is`(142))
                LiftCategory.OVERHEADPRESS -> assertThat(trainingMax.trainingMaxWeights, `is`(64))
                LiftCategory.DEADLIFT -> assertThat(trainingMax.trainingMaxWeights, `is`(186))
            }
            assertThat(trainingMax.methodCycle, `is`(userProgression.methodCycle))
            assertThat(trainingMax.phase, `is`(userProgression.phase))
        }
    }

    @Test
    fun givenTrainingMaxes_deleteTrainingMaxesByMethodCycle_worksAsExpected() = runTest {
        liftCategoryWeightsMap.forEach { (liftCategory, weights) ->
            val trainingMax = repository.createTrainingMax(liftCategory, weights, userProgression)
            repository.insertTrainingMax(trainingMax)
        }

        val methodCycleOther = MethodCycle(2)
        val userProgressionOther = userProgression.copy(methodCycle = methodCycleOther)
        liftCategoryWeightsMap.forEach { (liftCategory, weights) ->
            val trainingMax = repository.createTrainingMax(liftCategory, weights, userProgressionOther)
            repository.insertTrainingMax(trainingMax)
        }

        var trainingMaxes = repository.getAllTrainingMaxes().first()
        assertThat(trainingMaxes.isNotEmpty(), `is`(true))
        assertThat(trainingMaxes.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT * 2))

        repository.deleteTrainingMaxesByMethodCycle(methodCycleOther)
        trainingMaxes = repository.findTrainingMaxesByUserProgression(userProgressionOther)
        assertThat(trainingMaxes.isEmpty(), `is`(true))

        trainingMaxes = repository.getAllTrainingMaxes().first()
        assertThat(trainingMaxes.isNotEmpty(), `is`(true))
        assertThat(trainingMaxes.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))

        trainingMaxes = repository.findTrainingMaxesByUserProgression(userProgression)
        assertThat(trainingMaxes.isNotEmpty(), `is`(true))
        assertThat(trainingMaxes.size, `is`(LiftCategory.TOTAL_LIFT_CATEGORY_COUNT))
        trainingMaxes.forEach { trainingMax ->
            assertThat(trainingMax.methodCycle, `is`(userProgression.methodCycle))
        }
    }
}
package kr.valor.juggernaut.data.trainingmax

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TrainingMaxDaoTest {

    @get:Rule val rule = HiltAndroidRule(this)

    @Inject lateinit var trainingMaxDao: TrainingMaxDao

    @Before
    fun init() {
        rule.inject()
    }

    @After
    fun cleanUp() = runTest {
        trainingMaxDao.clear()
    }

    @Test
    fun givenTrainingMaxEntity_insert_get_delete_worksAsExpected() = runBlocking {
        val methodCycleValue = 1
        val phaseName = Phase.INITIAL.name
        val liftCategoryName = LiftCategory.BENCHPRESS.name
        val lastUpdatedAt = System.currentTimeMillis()
        val entity = TrainingMaxEntity(
            methodCycleValue = methodCycleValue,
            phaseName = phaseName,
            liftCategoryName = liftCategoryName,
            trainingMaxWeights = 60,
            lastUpdatedAt = lastUpdatedAt
        )

        val id = trainingMaxDao.insertTrainingMaxEntity(entity)

        val retrievedEntity = trainingMaxDao.findTrainingMaxEntitiesByMethodCycleAndPhase(
            methodCycleValue, phaseName
        )[0]
        assertThat(retrievedEntity.id, `is`(id))
        assertThat(retrievedEntity.lastUpdatedAt, `is`(lastUpdatedAt))
        assertThat(retrievedEntity.liftCategoryName, `is`(liftCategoryName))
        assertThat(retrievedEntity.methodCycleValue, `is`(methodCycleValue))
        assertThat(retrievedEntity.phaseName, `is`(phaseName))
        assertThat(retrievedEntity.trainingMaxWeights, `is`(60))

        trainingMaxDao.deleteTrainingMaxesByMethodCycle(methodCycleValue)
        val retrievedEntities = trainingMaxDao.getAllTrainingMaxEntities().first()
        assertThat(retrievedEntities.isEmpty(), `is`(true))
    }

}
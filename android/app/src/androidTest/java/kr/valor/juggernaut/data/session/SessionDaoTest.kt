package kr.valor.juggernaut.data.session

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.progression.model.UserProgression
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
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
class SessionDaoTest {

    @get:Rule val rule = HiltAndroidRule(this)

    @Inject lateinit var sessionDao: SessionDao

    @Before
    fun init() {
        rule.inject()
    }

    @After
    fun cleanUp() = runTest {
        sessionDao.clear()
    }

    @Test
    fun givenEmptyTable_getAllSessionEntities_returns_emptyList() = runBlocking {
        val entities = sessionDao.getAllSessionEntities().first()

        assertThat(entities.isEmpty(), `is`(true))
    }

    @Test
    fun givenEmptyTable_findSessionEntitiesByUserProgression_returns_emptyList() = runBlocking {
        val (methodCycleValue, phaseName, microCycleName) =
            UserProgression(MethodCycle(1), Phase.INITIAL, MicroCycle.INITIAL).serializedValue
        val entities = sessionDao.findSessionEntitiesByUserProgression(
             methodCycleValue, phaseName, microCycleName
        ).first()

        assertThat(entities.isEmpty(), `is`(true))
    }

    @Test
    fun given4Entities_findSessionEntitiesByUserProgression_and_getAllSessionEntities_returns_listWithSize4() = runBlocking {
        val (methodCycleValue, phaseName, microCycleName) =
            UserProgression(MethodCycle(1), Phase.INITIAL, MicroCycle.INITIAL).serializedValue
        val entity = SessionEntity(
            methodCycleValue = methodCycleValue,
            phaseName = phaseName,
            microCycleName = microCycleName,
            liftCategoryName = LiftCategory.BENCHPRESS.name,
            baseWeights = 60
        )
        val entities = listOf(
            entity,
            entity.copy(liftCategoryName = LiftCategory.SQUAT.name),
            entity.copy(liftCategoryName = LiftCategory.OVERHEADPRESS.name),
            entity.copy(liftCategoryName = LiftCategory.DEADLIFT.name)
        )
        entities.forEach { entityItem ->
            sessionDao.insertSessionEntity(entityItem)
        }

        var retrievedEntities = sessionDao.findSessionEntitiesByUserProgression(
            methodCycleValue, phaseName, microCycleName
        ).first()

        assertThat(retrievedEntities.isNotEmpty(), `is`(true))
        assertThat(retrievedEntities.size, `is`(4))

        retrievedEntities = sessionDao.getAllSessionEntities().first()

        assertThat(retrievedEntities.isNotEmpty(), `is`(true))
        assertThat(retrievedEntities.size, `is`(4))
    }

    @Test
    fun giveTwoEntityGroupsWithDifferentMethodCycle_deleteSessionEntitiesByMethodCycle_worksAsExpected() = runBlocking {
        val groupOneMethodCycleValue = 1
        val entityOneBase = SessionEntity(
            methodCycleValue = groupOneMethodCycleValue,
            phaseName = Phase.INITIAL.name,
            microCycleName = MicroCycle.INITIAL.name,
            liftCategoryName = LiftCategory.BENCHPRESS.name,
            baseWeights = 60
        )

        val groupTwoMethodCycleValue = 2
        val entityTwoBase = SessionEntity(
            methodCycleValue = groupTwoMethodCycleValue,
            phaseName = Phase.INITIAL.name,
            microCycleName = MicroCycle.INITIAL.name,
            liftCategoryName = LiftCategory.BENCHPRESS.name,
            baseWeights = 60
        )
        val entityGroupOne = listOf(
            entityOneBase,
            entityOneBase.copy(liftCategoryName = LiftCategory.SQUAT.name),
            entityOneBase.copy(liftCategoryName = LiftCategory.OVERHEADPRESS.name),
            entityOneBase.copy(liftCategoryName = LiftCategory.DEADLIFT.name)
        )
        val entityGroupTwo = listOf(
            entityTwoBase
        )

        (entityGroupOne + entityGroupTwo).forEach { entity ->
            sessionDao.insertSessionEntity(entity)
        }


        var retrievedEntities = sessionDao.getAllSessionEntities().first()
        assertThat(retrievedEntities.size, `is`(5))

        retrievedEntities = sessionDao.findSessionEntitiesByUserProgression(
            groupOneMethodCycleValue, Phase.INITIAL.name, MicroCycle.INITIAL.name
        ).first()
        assertThat(retrievedEntities.size, `is`(4))

        retrievedEntities = sessionDao.findSessionEntitiesByUserProgression(
            groupTwoMethodCycleValue, Phase.INITIAL.name, MicroCycle.INITIAL.name
        ).first()
        assertThat(retrievedEntities.size, `is`(1))


        sessionDao.deleteSessionEntitiesByMethodCycle(groupOneMethodCycleValue)
        retrievedEntities = sessionDao.findSessionEntitiesByUserProgression(
            groupOneMethodCycleValue, Phase.INITIAL.name, MicroCycle.INITIAL.name
        ).first()
        assertThat(retrievedEntities.isEmpty(), `is`(true))

        retrievedEntities = sessionDao.getAllSessionEntities().first()
        assertThat(retrievedEntities.isNotEmpty(), `is`(true))
        assertThat(retrievedEntities.size, `is`(1))

        sessionDao.deleteSessionEntitiesByMethodCycle(groupTwoMethodCycleValue)
        retrievedEntities = sessionDao.findSessionEntitiesByUserProgression(
            groupTwoMethodCycleValue, Phase.INITIAL.name, MicroCycle.INITIAL.name
        ).first()
        assertThat(retrievedEntities.isEmpty(), `is`(true))

        retrievedEntities = sessionDao.getAllSessionEntities().first()
        assertThat(retrievedEntities.isEmpty(), `is`(true))
    }

    @Test
    fun givenSessionEntity_insert_get_delete_worksAsExpected() = runBlocking {
        val entity = SessionEntity(
            methodCycleValue = 1,
            phaseName = Phase.INITIAL.name,
            microCycleName = MicroCycle.INITIAL.name,
            liftCategoryName = LiftCategory.BENCHPRESS.name,
            baseWeights = 60
        )
        val entityId = sessionDao.insertSessionEntity(entity)

        var retrievedEntity = sessionDao.findSessionEntityById(entityId).first()

        with(retrievedEntity) {
            assertThat(id, `is`(entityId))
            assertThat(methodCycleValue, `is`(entity.methodCycleValue))
            assertThat(phaseName, `is`(entity.phaseName))
            assertThat(microCycleName, `is`(entity.microCycleName))
            assertThat(liftCategoryName, `is`(entity.liftCategoryName))
            assertThat(baseWeights, `is`(entity.baseWeights))
        }

        sessionDao.deleteSessionEntity(retrievedEntity)

        retrievedEntity = sessionDao.findSessionEntityById(entityId).first()
        assertThat(retrievedEntity, nullValue())
    }

}
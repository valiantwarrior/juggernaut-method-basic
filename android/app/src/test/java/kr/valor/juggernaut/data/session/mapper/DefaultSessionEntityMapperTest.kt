package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class DefaultSessionEntityMapperTest {
    private lateinit var mapper: SessionMapper
    private lateinit var routineProvider: RoutineProviderDelegate<Session.Progression>
    private lateinit var baseEntity: SessionEntity

    @Before
    fun `init`() {
        mapper = TestServiceLocator.provideSessionMapper()
        routineProvider = TestServiceLocator.provideRoutineProviderDelegate()
        baseEntity = SessionEntity(
            methodCycle = 1,
            phaseName = Phase.REP10.name,
            microCycleName = MicroCycle.ACCUMULATION.name,
            liftCategoryName = LiftCategory.BENCH_PRESS.name,
            baseWeights = 60
        )
    }

    @Test
    fun `mapping when cycle is not DELOAD task works as expected (without SessionRecord)`() {
        val entity = baseEntity
        val model = mapper.map(entity)
        val entityFromModel = mapper.map(model, null)

        `comparing between entity and model - Not DELOAD `(model, entity)
        `comparing between two entities`(entity, entityFromModel)
    }

    @Test
    fun `mapping when cycle is not DELOAD task works as expected (with SessionRecord)`() {
        val entity = baseEntity
        val model = mapper.map(entity)

        val userCompleteTimeMillisRecord = System.currentTimeMillis()
        val userRepetitionsRecord = 16
        val userSessionRecord = SessionRecord(userRepetitionsRecord, userCompleteTimeMillisRecord)

        val entityFromModel = mapper.map(model, userSessionRecord)

        `comparing between two entities`(entity, entityFromModel)
        assertThat(entityFromModel.completeDateMillis, `is`(notNullValue()))
        assertThat(entityFromModel.completeDateMillis, `is`(userCompleteTimeMillisRecord))
        assertThat(entityFromModel.amrapRepetitions, `is`(notNullValue()))
        assertThat(entityFromModel.amrapRepetitions, `is`(userRepetitionsRecord))

        val modelFromEntity = mapper.map(entityFromModel)
        `comparing between entity and model - Not DELOAD `(modelFromEntity, entityFromModel)
        assertThat(modelFromEntity.amrapRoutine!!.reps, `is`(userRepetitionsRecord))
    }

    @Test
    fun `mapping when cycle is DELOAD works as expected (without SessionRecord)`() {
        val entity = baseEntity.copy(microCycleName = MicroCycle.DELOAD.name)
        val model = mapper.map(entity)
        val entityFromModel = mapper.map(model, null)

        `comparing between entity and model - DELOAD `(model, entity)
        `comparing between two entities`(entity, entityFromModel)
    }

    @Test
    fun `mapping when cycle is DELOAD works as expected (with SessionRecord)`() {
        val entity = baseEntity.copy(microCycleName = MicroCycle.DELOAD.name)
        val model = mapper.map(entity)

        val userCompleteTimeMillisRecord = System.currentTimeMillis()
        val userSessionRecord = SessionRecord(null, userCompleteTimeMillisRecord)

        val entityFromModel = mapper.map(model, userSessionRecord)

        `comparing between two entities`(entity, entityFromModel)
        assertThat(entityFromModel.completeDateMillis, `is`(notNullValue()))
        assertThat(entityFromModel.completeDateMillis, `is`(userCompleteTimeMillisRecord))
        assertThat(entityFromModel.amrapRepetitions, `is`(nullValue()))

        val modelFromEntity = mapper.map(entityFromModel)
        `comparing between entity and model - DELOAD `(modelFromEntity, entityFromModel)

    }

    private fun `comparing between entity and model - Not DELOAD `(model: Session, entity: SessionEntity) {
        `base entity model comparing`(model, entity)
        assertThat(model.amrapRoutine, `is`(notNullValue()))
        assertThat(model.warmupRoutines, `is`(notNullValue()))
        assertThat(model.deloadRoutines, `is`(nullValue()))
    }

    private fun `comparing between entity and model - DELOAD `(model: Session, entity: SessionEntity) {
        `base entity model comparing`(model, entity)
        assertThat(model.amrapRoutine, `is`(nullValue()))
        assertThat(model.warmupRoutines, `is`(nullValue()))
        assertThat(model.deloadRoutines, `is`(notNullValue()))
    }

    private fun `base entity model comparing`(model: Session, entity: SessionEntity) {
        assertThat(model.sessionId, `is`(entity.id))
        assertThat(model.methodCycle, `is`(entity.methodCycle))
        assertThat(model.progression.phase, `is`(Phase.valueOf(entity.phaseName)))
        assertThat(model.progression.microCycle, `is`(MicroCycle.valueOf(entity.microCycleName)))
        assertThat(model.category, `is`(LiftCategory.valueOf(entity.liftCategoryName)))
    }

    private fun `comparing between two entities`(actual: SessionEntity, expected: SessionEntity) {
        assertThat(actual.liftCategoryName, `is`(expected.liftCategoryName))
        assertThat(actual.phaseName, `is`(expected.phaseName))
        assertThat(actual.microCycleName, `is`(expected.microCycleName))
        assertThat(actual.methodCycle, `is`(expected.methodCycle))
        assertThat(actual.baseWeights, `is`(expected.baseWeights))
        assertThat(actual.id, `is`(expected.id))
    }
}
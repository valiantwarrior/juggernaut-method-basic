package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionProgression
import kr.valor.juggernaut.domain.session.model.SessionRecord
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class DefaultSessionEntityMapperTest {
    private lateinit var mapper: SessionMapper
    private lateinit var routineProvider: RoutineProviderDelegate<SessionProgression, List<Routine>>
    private lateinit var baseEntity: SessionEntity

    @Before
    fun `init`() {
        mapper = TestServiceLocator.provideSessionMapper()
        routineProvider = TestServiceLocator.provideRoutineProviderDelegate()
        baseEntity = SessionEntity(
            methodCycleValue = 1,
            phaseName = Phase.REP10.name,
            microCycleName = MicroCycle.ACCUMULATION.name,
            liftCategoryName = LiftCategory.BENCHPRESS.name,
            baseWeights = 60
        )
    }

    @Test
    fun `mapping when cycle is not DELOAD task works as expected (without SessionRecord)`() {
        val entity = baseEntity
        val model = mapper.mapEntity(entity)
        val entityFromModel = mapper.mapModel(model, null)

        `comparing between entity and model - Not DELOAD `(model, entity)
        `comparing between two entities`(entity, entityFromModel)
    }

    @Test
    fun `mapping when cycle is not DELOAD task works as expected (with SessionRecord)`() {
        val entity = baseEntity
        val model = mapper.mapEntity(entity)

        val userCompleteTimeMillisRecord = System.currentTimeMillis()
        val userRepetitionsRecord = 16
        val userSessionRecord = SessionRecord(userRepetitionsRecord, userCompleteTimeMillisRecord, 1)

        val entityFromModel = mapper.mapModel(model, userSessionRecord)

        `comparing between two entities`(entity, entityFromModel)
        assertThat(entityFromModel.completeDateMillis, `is`(notNullValue()))
        assertThat(entityFromModel.completeDateMillis, `is`(userCompleteTimeMillisRecord))
        assertThat(entityFromModel.amrapRepetitions, `is`(notNullValue()))
        assertThat(entityFromModel.amrapRepetitions, `is`(userRepetitionsRecord))

        val modelFromEntity = mapper.mapEntity(entityFromModel)
        `comparing between entity and model - Not DELOAD `(modelFromEntity, entityFromModel)
        assertThat(modelFromEntity.amrapRoutine!!.reps, `is`(userRepetitionsRecord))
    }

    @Test
    fun `mapping when cycle is DELOAD works as expected (without SessionRecord)`() {
        val entity = baseEntity.copy(microCycleName = MicroCycle.DELOAD.name)
        val model = mapper.mapEntity(entity)
        val entityFromModel = mapper.mapModel(model, null)

        `comparing between entity and model - DELOAD `(model, entity)
        `comparing between two entities`(entity, entityFromModel)
    }

    @Test
    fun `mapping when cycle is DELOAD works as expected (with SessionRecord)`() {
        val entity = baseEntity.copy(microCycleName = MicroCycle.DELOAD.name)
        val model = mapper.mapEntity(entity)

        val userCompleteTimeMillisRecord = System.currentTimeMillis()
        val userSessionRecord = SessionRecord(null, userCompleteTimeMillisRecord, 1)

        val entityFromModel = mapper.mapModel(model, userSessionRecord)

        `comparing between two entities`(entity, entityFromModel)
        assertThat(entityFromModel.completeDateMillis, `is`(notNullValue()))
        assertThat(entityFromModel.completeDateMillis, `is`(userCompleteTimeMillisRecord))
        assertThat(entityFromModel.amrapRepetitions, `is`(nullValue()))

        val modelFromEntity = mapper.mapEntity(entityFromModel)
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
        assertThat(model.sessionProgression.methodCycle.value, `is`(entity.methodCycleValue))
        assertThat(model.sessionProgression.phase, `is`(Phase.valueOf(entity.phaseName)))
        assertThat(model.sessionProgression.microCycle, `is`(MicroCycle.valueOf(entity.microCycleName)))
        assertThat(model.category, `is`(LiftCategory.valueOf(entity.liftCategoryName)))
    }

    private fun `comparing between two entities`(actual: SessionEntity, expected: SessionEntity) {
        assertThat(actual.liftCategoryName, `is`(expected.liftCategoryName))
        assertThat(actual.phaseName, `is`(expected.phaseName))
        assertThat(actual.microCycleName, `is`(expected.microCycleName))
        assertThat(actual.methodCycleValue, `is`(expected.methodCycleValue))
        assertThat(actual.baseWeights, `is`(expected.baseWeights))
        assertThat(actual.id, `is`(expected.id))
    }
}
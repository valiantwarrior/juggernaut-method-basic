package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.Session
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class DefaultEntityModelMapperTest {

    private lateinit var mapper: EntityModelMapper<SessionEntity, Session>

    private lateinit var accumulationSessionEntity: SessionEntity

    private lateinit var deloadSessionEntity: SessionEntity

    @Before
    fun `init`() {
        mapper = TestServiceLocator.provideEntityModelMapper()

        accumulationSessionEntity = SessionEntity(
            id = 0L,
            liftCategoryName = LiftCategory.BENCH_PRESS.name,
            phaseName = Phase.REP10.name,
            microCycleName = MicroCycle.ACCUMULATION.name,
            tmWeights = 60.0,
            repetitions = 10,
            date = System.currentTimeMillis()
        )

        deloadSessionEntity = accumulationSessionEntity.copy(id = 1L, microCycleName = MicroCycle.DELOAD.name)
    }

    @Test
    fun `validate mapper`() {
        var sessionModel = mapper.map(accumulationSessionEntity)
        with(accumulationSessionEntity) {
            assertThat(sessionModel.sessionId, `is`(id))
            assertThat(sessionModel.category, `is`(LiftCategory.valueOf(liftCategoryName)))
            assertThat(sessionModel.progression, `is`(Session.Progression(Phase.valueOf(phaseName), MicroCycle.valueOf(microCycleName))))
            assertThat(sessionModel.tmWeights, `is`(tmWeights))
            assertThat(sessionModel.amrapRoutine, notNullValue())
            assertThat(sessionModel.warmupRoutines, notNullValue())
            assertThat(sessionModel.deloadRoutines, nullValue())
        }

        sessionModel = mapper.map(deloadSessionEntity)
        with(deloadSessionEntity) {
            assertThat(sessionModel.sessionId, `is`(id))
            assertThat(sessionModel.category, `is`(LiftCategory.valueOf(liftCategoryName)))
            assertThat(sessionModel.progression, `is`(Session.Progression(Phase.valueOf(phaseName), MicroCycle.valueOf(microCycleName))))
            assertThat(sessionModel.tmWeights, `is`(tmWeights))
            assertThat(sessionModel.deloadRoutines, `is`(notNullValue()))
            assertThat(sessionModel.amrapRoutine,nullValue())
            assertThat(sessionModel.warmupRoutines, nullValue())
        }
    }
}
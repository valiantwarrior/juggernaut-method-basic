package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.data.common.mapper.EntityMapper
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.*

interface SessionMapper: EntityMapper<SessionEntity, Session> {

    fun mapModel(model: Session, params: SessionRecord?): SessionEntity

}
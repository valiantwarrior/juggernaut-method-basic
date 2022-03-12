package kr.valor.juggernaut.data.session

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.source.SessionDataSource

@Dao
interface SessionDao: SessionDataSource
package kr.valor.juggernaut.data.session

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression

@Dao
interface SessionDao: SessionDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertSessionEntity(entity: SessionEntity): Long

    @Delete
    override suspend fun deleteSessionEntity(entity: SessionEntity)

    @Update
    override suspend fun updateSessionEntity(entity: SessionEntity)

    @Query("DELETE FROM session_table WHERE method_cycle is :methodCycle")
    override suspend fun deleteSessionEntitiesByMethodCycle(methodCycle: Int)

    @Query("SELECT * FROM session_table WHERE id is :id")
    override fun findSessionEntityById(id: Long): Flow<SessionEntity>

    @Query("SELECT * FROM session_table WHERE id is :id")
    override suspend fun findSessionEntityByIdOneShot(id: Long): SessionEntity

    @Query("SELECT COUNT(complete_date) FROM session_table WHERE method_cycle is :methodCycleValue AND phase_name is :phaseName AND micro_cycle_name is :microCycleName")
    override suspend fun getCompletedSessionEntitiesCount(methodCycleValue: Int, phaseName: String, microCycleName: String): Int

    @Query("SELECT * FROM session_table WHERE method_cycle is :methodCycleValue AND phase_name is :phaseName AND micro_cycle_name is :microCycleName")
    override fun findSessionEntitiesByUserProgression(methodCycleValue: Int, phaseName: String, microCycleName: String): Flow<List<SessionEntity>>

    @Query("SELECT * FROM session_table ORDER BY id ASC")
    override fun getAllSessionEntities(): Flow<List<SessionEntity>>

    @Query("DELETE FROM session_table")
    override suspend fun clear()

}
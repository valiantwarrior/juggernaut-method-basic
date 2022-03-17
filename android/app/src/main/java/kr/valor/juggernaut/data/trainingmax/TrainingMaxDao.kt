package kr.valor.juggernaut.data.trainingmax

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity
import kr.valor.juggernaut.data.trainingmax.source.TrainingMaxDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression

@Dao
interface TrainingMaxDao: TrainingMaxDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertTrainingMaxEntity(entity: TrainingMaxEntity): Long

    @Query("SELECT * FROM training_max_table WHERE method_cycle is :methodCycleValue AND phase_name is :phaseName")
    override suspend fun findTrainingMaxEntitiesByMethodCycleAndPhase(methodCycleValue: Int, phaseName: String): List<TrainingMaxEntity>

    @Query("DELETE FROM training_max_table WHERE method_cycle is :methodCycle")
    override suspend fun deleteTrainingMaxesByMethodCycle(methodCycle: Int)

    @Query("SELECT * FROM training_max_table ORDER BY id ASC")
    override fun getAllTrainingMaxEntities(): Flow<List<TrainingMaxEntity>>

    @Query("DELETE FROM training_max_table")
    override suspend fun clear()

}
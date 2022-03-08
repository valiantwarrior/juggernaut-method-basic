package kr.valor.juggernaut.data.session.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "category")
    val liftCategoryName: String,

    @ColumnInfo(name = "phase")
    val phaseName: String,

    @ColumnInfo(name = "micro_cycle")
    val microCycleName: String,

    @ColumnInfo(name = "current_tm_weights")
    val tmWeights: Double,

    @ColumnInfo(name = "training_weights")
    val trainingWeights: Double = 0.0,

    @ColumnInfo(name = "complete_repetitions")
    val completeRepetitions: Int = 0,

    @ColumnInfo(name = "complete_date")
    val completeDate: Long? = null
)
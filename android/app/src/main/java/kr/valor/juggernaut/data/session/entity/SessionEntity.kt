package kr.valor.juggernaut.data.session.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "method_cycle")
    val methodCycleValue: Int,

    @ColumnInfo(name = "phase_name")
    val phaseName: String,

    @ColumnInfo(name = "micro_cycle_name")
    val microCycleName: String,

    @ColumnInfo(name = "lift_category_name")
    val liftCategoryName: String,

    @ColumnInfo(name = "current_tm_weights")
    val baseWeights: Int,

    @ColumnInfo(name = "amrap_repetitions")
    val amrapRepetitions: Int? = null,

    @ColumnInfo(name = "complete_date")
    val completeDateMillis: Long? = null,

    @ColumnInfo(name = "session_ordinal_of_week")
    val sessionOrdinal: Int? = null
)
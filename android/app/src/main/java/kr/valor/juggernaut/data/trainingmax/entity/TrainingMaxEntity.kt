package kr.valor.juggernaut.data.trainingmax.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_max_table") // update when realization cycle done
data class TrainingMaxEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "method_cycle")
    val methodCycleValue: Int,

    @ColumnInfo(name = "phase_name")
    val phaseName: String,

    @ColumnInfo(name = "lift_category_name")
    val liftCategoryName: String,

    @ColumnInfo(name = "training_max_weights")
    val trainingMaxWeights: Int,

    @ColumnInfo(name = "last_updated_at")
    val lastUpdatedAt: Long
)

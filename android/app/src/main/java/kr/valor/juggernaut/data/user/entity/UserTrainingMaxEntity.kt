package kr.valor.juggernaut.data.user.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_training_max_table") // update when realization cycle done
data class UserTrainingMaxEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "lift_category_name")
    val liftCategoryName: String,

    @ColumnInfo(name = "training_max_weights")
    val trainingMaxWeights: Double,

    @ColumnInfo(name = "last_updated_at")
    val lastUpdatedAt: Long
)

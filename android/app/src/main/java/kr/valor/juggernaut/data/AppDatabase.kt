package kr.valor.juggernaut.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.valor.juggernaut.data.AppDatabase.Companion.DATABASE_VERSION
import kr.valor.juggernaut.data.session.SessionDao
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.trainingmax.TrainingMaxDao
import kr.valor.juggernaut.data.trainingmax.entity.TrainingMaxEntity

@Database(
    entities = [SessionEntity::class, TrainingMaxEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract val sessionDao: SessionDao
    abstract val trainingMaxDao: TrainingMaxDao

    companion object {
        const val DATABASE_NAME = "juggernaut-basic-method.db"
        const val DATABASE_VERSION = 1
    }
}
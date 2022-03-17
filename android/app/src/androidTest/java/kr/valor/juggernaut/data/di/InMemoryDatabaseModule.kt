package kr.valor.juggernaut.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kr.valor.juggernaut.data.AppDatabase
import kr.valor.juggernaut.data.session.SessionDao
import kr.valor.juggernaut.data.trainingmax.TrainingMaxDao
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppDatabaseModule::class]
)
object InMemoryDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(applicationContext, AppDatabase::class.java)
            .build()

    @Provides
    fun provideSessionDao(database: AppDatabase): SessionDao =
        database.sessionDao

    @Provides
    fun provideTrainingMaxDao(database: AppDatabase): TrainingMaxDao =
        database.trainingMaxDao

}
package kr.valor.juggernaut.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.AppDatabase
import kr.valor.juggernaut.data.AppDatabase.Companion.DATABASE_NAME
import kr.valor.juggernaut.data.session.SessionDao
import kr.valor.juggernaut.data.trainingmax.TrainingMaxDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME )
            .build()

    @Provides
    fun provideSessionDao(database: AppDatabase): SessionDao =
        database.sessionDao

    @Provides
    fun provideTrainingMaxDao(database: AppDatabase): TrainingMaxDao =
        database.trainingMaxDao

}
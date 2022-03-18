package kr.valor.juggernaut.data.trainingmax.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.trainingmax.TrainingMaxDao
import kr.valor.juggernaut.data.trainingmax.source.TrainingMaxDataSource

@InstallIn(SingletonComponent::class)
@Module
abstract class TrainingMaxDataSourceModule {

    @Binds
    abstract fun bindTrainingMaxDataSource(trainingMaxDao: TrainingMaxDao): TrainingMaxDataSource

}
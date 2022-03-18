package kr.valor.juggernaut.data.trainingmax.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.trainingmax.mapper.DefaultTrainingMaxMapper
import kr.valor.juggernaut.data.trainingmax.mapper.TrainingMaxMapper

@InstallIn(SingletonComponent::class)
@Module
abstract class TrainingMaxMapperModule {

    @Binds
    abstract fun bindTrainingMaxMapper(impl: DefaultTrainingMaxMapper): TrainingMaxMapper

}
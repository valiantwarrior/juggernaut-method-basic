package kr.valor.juggernaut.data.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.valor.juggernaut.data.common.converter.KgWeightUnitTransformer
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KgWeightUnit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LbWeightUnit

@InstallIn(SingletonComponent::class)
@Module
abstract class WeightUnitTransformerModule {

    @KgWeightUnit
    @Singleton
    @Binds
    abstract fun bindWeightUnitTransformer(impl: KgWeightUnitTransformer): WeightUnitTransformer

}
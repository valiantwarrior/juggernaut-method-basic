package kr.valor.juggernaut.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStoreScope

@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object TestCoroutineScopeModule {

    @DataStoreScope
    @Provides
    @Singleton
    fun provideTestCoroutineScope(): TestScope = TestScope(StandardTestDispatcher())

}
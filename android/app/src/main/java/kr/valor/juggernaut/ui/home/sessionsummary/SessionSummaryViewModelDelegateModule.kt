package kr.valor.juggernaut.ui.home.sessionsummary

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
@Suppress("UNUSED")
abstract class SessionSummaryViewModelDelegateModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindSessionSummaryViewModelDelegate(
        impl: SessionSummaryViewModelDelegateImpl
    ): SessionSummaryViewModelDelegate

}
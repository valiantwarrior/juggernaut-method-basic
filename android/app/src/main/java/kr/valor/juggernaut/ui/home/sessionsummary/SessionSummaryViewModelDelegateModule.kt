package kr.valor.juggernaut.ui.home.sessionsummary

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ViewModelComponent::class)
@Module
@Suppress("UNUSED")
abstract class SessionSummaryViewModelDelegateModule {

    @ViewModelScoped
    @Binds
    abstract fun bindSessionSummaryViewModelDelegate(
        impl: SessionSummaryViewModelDelegateImpl
    ): SessionSummaryViewModelDelegate

}
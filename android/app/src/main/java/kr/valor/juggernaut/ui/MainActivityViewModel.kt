package kr.valor.juggernaut.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kr.valor.juggernaut.domain.settings.model.Theme
import kr.valor.juggernaut.domain.settings.usecase.LoadThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    loadThemeUseCase: LoadThemeUseCase
): ViewModel() {
    val theme = loadThemeUseCase()
}
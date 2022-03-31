package kr.valor.juggernaut.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.common.HaltMethodStateContract
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val haltMethodStateContract: HaltMethodStateContract,
    loadProgressionStateUseCase: LoadProgressionStateUseCase
) : ViewModel() {

    private val currentProgressionState = loadProgressionStateUseCase()

    private val _haltEventFlow = MutableSharedFlow<SettingsHaltEvent>()
    val haltEventFlow: Flow<SettingsHaltEvent>
        get() = _haltEventFlow

    fun onHaltMethodClicked() {
        viewModelScope.launch {
            haltMethodStateContract()
            _haltEventFlow.emit(SettingsHaltEvent.Finish)
        }
    }

}

sealed class SettingsHaltEvent {
    object Finish: SettingsHaltEvent()
}
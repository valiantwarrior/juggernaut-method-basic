package kr.valor.juggernaut.ui.onboarding.form.page.input

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.valor.juggernaut.ui.NAV_ARGS_ONBOARDING_FORM_PAGE_KEY
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition
import kr.valor.juggernaut.ui.onboarding.form.FormRepository
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: FormRepository
): ViewModel() {

    private val currentPagePosition: FormPagePosition =
        savedStateHandle[NAV_ARGS_ONBOARDING_FORM_PAGE_KEY]!!

    private val inputUiEvent = MutableSharedFlow<InputUiEvent>()
    val uiEvent: Flow<InputUiEvent>
        get() = inputUiEvent

    val plateStack = repository.platesStackMap[currentPagePosition]!!

    fun pushPlate(plate: Double) {
        val upcomingBarbellState = (plateStack.value + plate).sumOf { eachPlate ->
            PLATE_LOOKUP_TABLE[eachPlate]!!.plateThickness
        }

        if (upcomingBarbellState <= BARBELL_SLEEVE_LENGTH) {
            repository.pushPlates(currentPagePosition, plate)
        } else {
            viewModelScope.launch {
                inputUiEvent.emit(InputUiEvent.BarbellFull)
            }
        }
    }

    fun popPlate() {
        if (!repository.popPlates(currentPagePosition)) {
            viewModelScope.launch {
                inputUiEvent.emit(InputUiEvent.BarbellEmpty)
            }
        }
    }

}

sealed class InputUiEvent {
    object BarbellEmpty: InputUiEvent()
    object BarbellFull : InputUiEvent()
}
package kr.valor.juggernaut.ui.onboarding.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.common.StartMethodSetupContract
import kr.valor.juggernaut.domain.trainingmax.model.CorrespondingBaseRecord
import kr.valor.juggernaut.domain.trainingmax.usecase.CalculateOneRepMaxUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.CalculateTrainingMaxWeightsUseCase
import kr.valor.juggernaut.ui.onboarding.form.FormRepository
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    repository: FormRepository,
    private val startMethodSetupContract: StartMethodSetupContract,
    private val calculateTrainingMaxWeightsUseCase: CalculateTrainingMaxWeightsUseCase,
    private val calculateOneRepMaxUseCase: CalculateOneRepMaxUseCase
): ViewModel() {

    val formPageDataMap = repository.formPageDataMap

    private val _confirmationUiEventFlow = MutableSharedFlow<ConfirmationUiEvent>()
    val confirmationUiEvent: Flow<ConfirmationUiEvent>
        get() = _confirmationUiEventFlow

    fun onClickStartMethod() {
        viewModelScope.launch {
            val liftCategoryPageDataMap = formPageDataMap.mapKeys { (key, _) ->
                LiftCategory.values().find { liftCategory ->  liftCategory.name == key.name }!!
            }
            val estimatedTrainingMaxAndCorrespondingBaseRecordPairMap: Map<LiftCategory, Pair<Int, CorrespondingBaseRecord>> =
                liftCategoryPageDataMap.mapValues { (_, pageDataStateFlow) ->
                    val (inputWeights, inputRepetitions) = pageDataStateFlow.value
                    val estimatedOneRepMax = calculateOneRepMaxUseCase(inputWeights.toDouble(), inputRepetitions)
                    val newTrainingMax = calculateTrainingMaxWeightsUseCase(estimatedOneRepMax)

                    newTrainingMax to CorrespondingBaseRecord(baseWeights = inputWeights.toDouble(), baseRepetitions = inputRepetitions)
                }

            startMethodSetupContract(estimatedTrainingMaxAndCorrespondingBaseRecordPairMap)
            _confirmationUiEventFlow.emit(ConfirmationUiEvent.Finish)
        }
    }

}

sealed class ConfirmationUiEvent {
    object Finish: ConfirmationUiEvent()
}
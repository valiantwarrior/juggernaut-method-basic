package kr.valor.juggernaut.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.domain.common.StartMethodSetupContract
import kr.valor.juggernaut.domain.trainingmax.usecase.CalculateOneRepMaxUseCase
import kr.valor.juggernaut.domain.trainingmax.usecase.CalculateTrainingMaxWeightsUseCase
import kr.valor.juggernaut.ui.onboarding.OnboardingUiModel.InputModel.Companion.INITIAL_INPUT_REPETITIONS_TEXT
import kr.valor.juggernaut.ui.onboarding.OnboardingUiModel.InputModel.Companion.INITIAL_INPUT_WEIGHTS_TEXT
import javax.inject.Inject

private typealias InputModel = OnboardingUiModel.InputModel

sealed class OnboardingUiEvent {
    data class Next(val nextPagePosition: Int): OnboardingUiEvent()
    data class Previous(val previousPagePosition: Int): OnboardingUiEvent()
    object Done: OnboardingUiEvent()
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val startMethodSetupContract: StartMethodSetupContract,
    private val calculateTrainingMaxWeightsUseCase: CalculateTrainingMaxWeightsUseCase,
    private val calculateOneRepMaxUseCase: CalculateOneRepMaxUseCase
): ViewModel() {

    private val _eventChannel: Channel<OnboardingUiEvent> = Channel()
    val uiEventFlow: Flow<OnboardingUiEvent>
        get() = _eventChannel.receiveAsFlow()

    private val _uiModel: MutableStateFlow<Map<LiftCategory, OnboardingUiModel>> = MutableStateFlow(mapOf())
    val uiModel: StateFlow<Map<LiftCategory, OnboardingUiModel>>
        get() = _uiModel

    val inputWeightsFieldState = MutableStateFlow(INITIAL_INPUT_WEIGHTS_TEXT)

    val inputRepetitionsFieldState = MutableStateFlow(INITIAL_INPUT_REPETITIONS_TEXT)

    fun onClickSubmit(currentPagePosition: Int) {
        viewModelScope.launch {
            val inputWeightsValue = inputWeightsFieldState.value.toDouble()
            val inputRepetitionsValue = inputRepetitionsFieldState.value.toInt()
            val estimatedOneRepMax = calculateOneRepMaxUseCase(inputWeightsValue, inputRepetitionsValue)
            val estimatedTrainingMax = calculateTrainingMaxWeightsUseCase(estimatedOneRepMax)
            val correspondingLiftCategory = getCorrespondingLiftCategoryByPagePosition(currentPagePosition)
            val uiModelState = _uiModel.value.toMutableMap()

            uiModelState[correspondingLiftCategory] = OnboardingUiModel(
                estimatedOneRepMax = estimatedOneRepMax,
                estimatedTrainingMax = estimatedTrainingMax,
                inputModel = InputModel(
                    inputWeights = inputWeightsValue,
                    inputRepetitions = inputRepetitionsValue
                )
            )

            _uiModel.value = uiModelState.toMap()
            _eventChannel.send(OnboardingUiEvent.Next(currentPagePosition + 1))

            if (currentPagePosition == OnboardingPagePosition.values().size - 1) {
                return@launch
            } else {
                prepareNextPageState(currentPagePosition + 1)
            }
        }
    }

    fun onClickBack(previousPagePosition: Int) {
        if (previousPagePosition < 0) {
            return
        } else {
            viewModelScope.launch {
                val correspondingLiftCategory = getCorrespondingLiftCategoryByPagePosition(previousPagePosition)
                val correspondingInputModel = uiModel.value[correspondingLiftCategory]?.inputModel

                inputWeightsFieldState.value = correspondingInputModel?.inputWeights?.toString()
                    ?: INITIAL_INPUT_WEIGHTS_TEXT
                inputRepetitionsFieldState.value = correspondingInputModel?.inputRepetitions?.toString()
                    ?: INITIAL_INPUT_REPETITIONS_TEXT

                _eventChannel.send(OnboardingUiEvent.Previous(previousPagePosition))
            }
        }
    }

    fun onClickComplete() {
        viewModelScope.launch {
            val liftCategoryWeightsMap = uiModel.value.mapValues { (_, onboardingModel) ->
                onboardingModel.estimatedTrainingMax
            }
            startMethodSetupContract(liftCategoryWeightsMap)
            _eventChannel.send(OnboardingUiEvent.Done)
        }
    }

    private fun prepareNextPageState(nextPagePosition: Int) {
        val correspondingLiftCategory = getCorrespondingLiftCategoryByPagePosition(nextPagePosition)
        val uiModelState = uiModel.value

        uiModelState[correspondingLiftCategory]?.let { uiModel ->
            val inputModel = uiModel.inputModel
            inputWeightsFieldState.value = inputModel.inputWeights.toString()
            inputRepetitionsFieldState.value = inputModel.inputRepetitions.toString()
        } ?: run {
            inputWeightsFieldState.value = INITIAL_INPUT_WEIGHTS_TEXT
            inputRepetitionsFieldState.value = INITIAL_INPUT_REPETITIONS_TEXT
        }
    }

    private fun getCorrespondingLiftCategoryByPagePosition(pagePosition: Int) =
        OnboardingPagePosition.values()[pagePosition].correspondingLiftCategory

}

data class OnboardingUiModel(
    val estimatedOneRepMax: Double,
    val estimatedTrainingMax: Int,
    val inputModel: InputModel
) {

    data class InputModel(
        val inputWeights: Double = 0.0,
        val inputRepetitions: Int = 0
    ) {

        companion object {
            const val INITIAL_INPUT_WEIGHTS_TEXT = "0.0"
            const val INITIAL_INPUT_REPETITIONS_TEXT = "0"
        }
    }

}

enum class OnboardingPagePosition(val correspondingLiftCategory: LiftCategory) {
    BP(LiftCategory.BENCHPRESS),
    SQ(LiftCategory.SQUAT),
    OHP(LiftCategory.OVERHEADPRESS),
    DL(LiftCategory.DEADLIFT)
}
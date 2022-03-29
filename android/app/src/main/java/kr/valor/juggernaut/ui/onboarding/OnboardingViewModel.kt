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
import kr.valor.juggernaut.domain.trainingmax.model.CorrespondingBaseRecord
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

    private val _userInputModelState: MutableStateFlow<Map<LiftCategory, OnboardingUiModel>> = MutableStateFlow(mapOf())
    val userInputModelState: StateFlow<Map<LiftCategory, OnboardingUiModel>>
        get() = _userInputModelState

    val inputWeightsFieldState = MutableStateFlow(INITIAL_INPUT_WEIGHTS_TEXT)

    val inputRepetitionsFieldState = MutableStateFlow(INITIAL_INPUT_REPETITIONS_TEXT)

    fun onClickSubmit(currentPagePosition: Int) {
        viewModelScope.launch {
            saveCurrentPageUserInputState(currentPagePosition)
            prepareNextPageState(currentPagePosition + 1)
            _eventChannel.send(OnboardingUiEvent.Next(currentPagePosition + 1))
        }
    }

    fun onClickBack(previousPagePosition: Int) {
        viewModelScope.launch {
            if (previousPagePosition > 0) {
                val currentPagePosition = previousPagePosition + 1
                saveCurrentPageUserInputState(currentPagePosition)

                val correspondingLiftCategory = getCorrespondingLiftCategoryByPagePosition(previousPagePosition)
                val correspondingInputModel = userInputModelState.value[correspondingLiftCategory]?.inputModel

                inputWeightsFieldState.value = correspondingInputModel?.inputWeights?.toString()
                    ?: INITIAL_INPUT_WEIGHTS_TEXT
                inputRepetitionsFieldState.value = correspondingInputModel?.inputRepetitions?.toString()
                    ?: INITIAL_INPUT_REPETITIONS_TEXT
            }
            _eventChannel.send(OnboardingUiEvent.Previous(previousPagePosition))
        }
    }

    fun onClickComplete() {
        viewModelScope.launch {
            val estimatedTrainingMaxAndCorrespondingBaseRecordPairMap =
                userInputModelState.value.mapValues { (_, onboardingModel) ->
                    val newTrainingMax = onboardingModel.estimatedTrainingMax
                    val correspondingBaseRecord = CorrespondingBaseRecord(
                        baseWeights = onboardingModel.inputModel.inputWeights,
                        baseRepetitions = onboardingModel.inputModel.inputRepetitions
                    )

                    newTrainingMax to correspondingBaseRecord
                }

            startMethodSetupContract(estimatedTrainingMaxAndCorrespondingBaseRecordPairMap)
            _eventChannel.send(OnboardingUiEvent.Done)
        }
    }

    private fun saveCurrentPageUserInputState(pagePosition: Int) {
        val inputWeightsValue = inputWeightsFieldState.value.toDouble()
        val inputRepetitionsValue = inputRepetitionsFieldState.value.toInt()
        val estimatedOneRepMax = calculateOneRepMaxUseCase(inputWeightsValue, inputRepetitionsValue)
        val estimatedTrainingMax = calculateTrainingMaxWeightsUseCase(estimatedOneRepMax)
        val correspondingLiftCategory = getCorrespondingLiftCategoryByPagePosition(pagePosition)
        val uiModelState = _userInputModelState.value.toMutableMap()

        correspondingLiftCategory ?: return // which means that current page is FOOTER

        uiModelState[correspondingLiftCategory] = OnboardingUiModel(
            estimatedOneRepMax = estimatedOneRepMax,
            estimatedTrainingMax = estimatedTrainingMax,
            inputModel = InputModel(
                inputWeights = inputWeightsValue,
                inputRepetitions = inputRepetitionsValue
            )
        )

        _userInputModelState.value = uiModelState.toMap()
    }

    private fun prepareNextPageState(nextPagePosition: Int) {
        val correspondingLiftCategory = getCorrespondingLiftCategoryByPagePosition(nextPagePosition)
        val uiModelState = userInputModelState.value

        correspondingLiftCategory ?: return // which means that current page is FOOTER

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

enum class OnboardingPagePosition(val correspondingLiftCategory: LiftCategory?) {
    HEADER(null),
    BP(LiftCategory.BENCHPRESS),
    SQ(LiftCategory.SQUAT),
    OHP(LiftCategory.OVERHEADPRESS),
    DL(LiftCategory.DEADLIFT),
    FOOTER(null)
}
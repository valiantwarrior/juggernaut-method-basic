package kr.valor.juggernaut.ui.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import kr.valor.juggernaut.domain.trainingmax.usecase.LoadTrainingMaxesUseCase
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    loadTrainingMaxesUseCase: LoadTrainingMaxesUseCase
) : ViewModel() {

    val uiState: StateFlow<StatisticUiState> = loadTrainingMaxesUseCase().map { trainingMaxes ->
        StatisticUiState.Result(trainingMaxes = trainingMaxes)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), StatisticUiState.Loading)

}

sealed class StatisticUiState {
    object Loading: StatisticUiState()
    data class Result(val trainingMaxes: List<TrainingMax>): StatisticUiState()
}
package kr.valor.juggernaut.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.valor.juggernaut.domain.common.StartMethodSetupContract
import kr.valor.juggernaut.domain.progression.model.ProgressionState
import kr.valor.juggernaut.domain.progression.usecase.usecase.LoadProgressionStateUseCase
import kr.valor.juggernaut.domain.session.usecase.contract.SynchronizeSessionsContract
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val startMethodSetupContract: StartMethodSetupContract,
    private val synchronizeSessionsContract: SynchronizeSessionsContract,
    loadProgressionStateUseCase: LoadProgressionStateUseCase
): ViewModel() {

    private val _uiStateLiveData = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState>
        get() = _uiStateLiveData

    init {
        viewModelScope.launch {
            _uiStateLiveData.value = HomeUiState.Loading

            val homeUiState = when(loadProgressionStateUseCase().first()) {
                is ProgressionState.None -> throw IllegalStateException()
                is ProgressionState.OnGoing -> HomeUiState.OnGoing
                is ProgressionState.Done -> HomeUiState.Done
            }

            _uiStateLiveData.value = homeUiState
        }
    }

    fun onClickStartMethodWithPreviousRecord() {
        viewModelScope.launch {
            startMethodSetupContract(null, true)
            synchronizeSessionsContract()
            _uiStateLiveData.value = HomeUiState.OnGoing
        }
    }

}

sealed class HomeUiState {
    object Done: HomeUiState()
    object OnGoing: HomeUiState()
    object Loading: HomeUiState()
}
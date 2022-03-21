package kr.valor.juggernaut.ui.session.record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.valor.juggernaut.domain.session.usecase.usecase.FindSessionOneShotUseCase
import kr.valor.juggernaut.ui.NAV_ARGS_SESSION_ID_KEY
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findSessionOneShotUseCase: FindSessionOneShotUseCase
) : ViewModel() {

    val session = liveData {
        val sessionId: Long = savedStateHandle.get(NAV_ARGS_SESSION_ID_KEY)!!
        emit(findSessionOneShotUseCase(sessionId))
    }

}
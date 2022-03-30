package kr.valor.juggernaut.ui.onboarding.form.page

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition
import kr.valor.juggernaut.ui.onboarding.form.FormRepository
import kr.valor.juggernaut.ui.onboarding.form.page.FormPageFragment.Companion.PAGE_POSITION_KEY
import javax.inject.Inject

@HiltViewModel
class FormPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: FormRepository
): ViewModel() {

    val pagePosition: FormPagePosition =
        FormPagePosition.valueOf(savedStateHandle[PAGE_POSITION_KEY]!!)

    val formPageData = repository.formPageDataMap[pagePosition]!!

    fun onClickPlusRep() {
        repository.increaseRepetition(pagePosition)
    }

    fun onClickMinusRep() {
        repository.decreaseRepetition(pagePosition)
    }

}
package kr.valor.juggernaut.ui.onboarding.form

import androidx.annotation.VisibleForTesting
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kr.valor.juggernaut.ui.onboarding.form.page.FormPageData
import kr.valor.juggernaut.ui.onboarding.form.FormPagePosition.*
import javax.inject.Inject

@ActivityRetainedScoped
class FormRepository @Inject constructor() {

    private val _plateStackMap: Map<FormPagePosition, MutableStateFlow<List<Double>>> = mapOf(
        BENCHPRESS to MutableStateFlow(emptyList()),
        SQUAT to MutableStateFlow(emptyList()),
        OVERHEADPRESS to MutableStateFlow(emptyList()),
        DEADLIFT to MutableStateFlow(emptyList())
    )
    val platesStackMap: Map<FormPagePosition, StateFlow<List<Double>>>
        get() = _plateStackMap

    private val _formPageDataMap: Map<FormPagePosition, MutableStateFlow<FormPageData>> = mapOf(
        BENCHPRESS to MutableStateFlow(FormPageData()),
        SQUAT to MutableStateFlow(FormPageData()),
        OVERHEADPRESS to MutableStateFlow(FormPageData()),
        DEADLIFT to MutableStateFlow(FormPageData())
    )
    val formPageDataMap: Map<FormPagePosition, StateFlow<FormPageData>>
        get() = _formPageDataMap

    fun increaseRepetition(pagePosition: FormPagePosition) {
        val currentPageData = getCurrentPageData(pagePosition)
        val currentRepetitions = currentPageData.inputRepetitions

        setCurrentPageData(pagePosition, currentPageData.copy(inputRepetitions = currentRepetitions + 1))
    }

    fun decreaseRepetition(pagePosition: FormPagePosition) {
        val currentPageData = getCurrentPageData(pagePosition)
        val currentRepetitions = currentPageData.inputRepetitions
        if (currentRepetitions == 0) {
            return
        }

        setCurrentPageData(pagePosition, currentPageData.copy(inputRepetitions = currentRepetitions - 1))
    }

    fun pushPlates(pagePosition: FormPagePosition, plate: Double): Double {
        val currentPageData = getCurrentPageData(pagePosition)
        val currentWeights = currentPageData.inputWeights
        val nextPagePlateStack =
            (getCurrentPagePlateStack(pagePosition) + plate).sortedDescending()

        setCurrentPagePlateStack(pagePosition, nextPagePlateStack)
        setCurrentPageData(pagePosition, currentPageData.copy(inputWeights = currentWeights + (plate * 2).toInt()))

        return nextPagePlateStack.sum()
    }

    fun popPlates(pagePosition: FormPagePosition): Boolean {
        val currentPageData = getCurrentPageData(pagePosition)
        val currentWeights = currentPageData.inputWeights
        val currentPagePlateStack = getCurrentPagePlateStack(pagePosition)

        return if (currentPagePlateStack.isEmpty()) {
            false
        } else {
            val topPlateStackItem = currentPagePlateStack.last()
            val newPlateStack = currentPagePlateStack - topPlateStackItem

            setCurrentPagePlateStack(pagePosition, newPlateStack)
            setCurrentPageData(pagePosition, currentPageData.copy(inputWeights = currentWeights - (topPlateStackItem * 2).toInt()))

            true
        }
    }

    @VisibleForTesting
    fun clear() {
        FormPagePosition.values().forEach {
            _plateStackMap[it]!!.value = emptyList()
            _formPageDataMap[it]!!.value = FormPageData()
        }
    }

    private fun setCurrentPagePlateStack(pagePosition: FormPagePosition, plateStack: List<Double>) {
        _plateStackMap[pagePosition]!!.value = plateStack
    }

    private fun getCurrentPagePlateStack(pagePosition: FormPagePosition) =
        _plateStackMap[pagePosition]!!.value

    private fun getCurrentPageData(pagePosition: FormPagePosition) =
        formPageDataMap[pagePosition]!!.value

    private fun setCurrentPageData(pagePosition: FormPagePosition, formPageData: FormPageData) {
        _formPageDataMap[pagePosition]!!.value = formPageData
    }

}
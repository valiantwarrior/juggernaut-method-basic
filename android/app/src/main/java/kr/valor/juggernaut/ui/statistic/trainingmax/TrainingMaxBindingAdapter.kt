package kr.valor.juggernaut.ui.statistic.trainingmax

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.LiftCategory.*
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import kr.valor.juggernaut.ui.common.getLiftCategoryIcon
import kr.valor.juggernaut.ui.common.toFormattedString
import kr.valor.juggernaut.ui.common.toLocalDateTime
import kr.valor.juggernaut.ui.statistic.StatisticUiState

@BindingAdapter("statisticUserTrainingMaxItems")
fun RecyclerView.bindStatisticUserTrainingMaxItems(uiState: StatisticUiState) {
    bindUiState(uiState) { result ->
        val trainingMaxes = result.trainingMaxes

        (adapter as TrainingMaxAdapter).submitList(trainingMaxes)
    }
}

private inline fun bindUiState(uiState: StatisticUiState, block:(StatisticUiState.Result) -> Unit) {
    if (uiState !is StatisticUiState.Result) {
        return
    }
    block(uiState)
}

@BindingAdapter("statisticUserTrainingMaxLiftCategoryIdentityColor")
fun View.bindUserTrainingMaxLiftCategoryIdentityColor(trainingMax: TrainingMax?) {
    trainingMax ?: return

    @ColorInt val liftCategoryIdentityColor = when(trainingMax.liftCategory) {
        BENCHPRESS -> R.color.lift_category_bp_identity_color
        SQUAT -> R.color.lift_category_sq_identity_color
        OVERHEADPRESS -> R.color.lift_category_ohp_identity_color
        DEADLIFT -> R.color.lift_category_dl_identity_color
    }
    setBackgroundResource(liftCategoryIdentityColor)
}

@BindingAdapter("statisticUserTrainingMaxLiftCategoryIcon")
fun ImageView.bindUserTrainingMaxLiftCategoryIcon(trainingMax: TrainingMax?) {
    trainingMax ?: return

    @DrawableRes val liftCategoryIconRes = getLiftCategoryIcon(trainingMax.liftCategory)

    setImageResource(liftCategoryIconRes)
}

// TODO("Considering lbs unit later")
@BindingAdapter("statisticUserTrainingMaxWeights")
fun TextView.bindUserTrainingMaxWeights(trainingMax: TrainingMax?) {
    trainingMax ?: return

    @StringRes val stringFormatId = R.string.statistic_user_training_max_tm_weights_text_format

    text = resources.getString(stringFormatId, trainingMax.trainingMaxWeights, "kg")
}

// TODO("Considering lbs unit later")
@BindingAdapter("statisticUserTrainingMaxBaseRecord")
fun TextView.bindUserTrainingMaxBaseRecord(trainingMax: TrainingMax?) {
    trainingMax ?: return

    @StringRes val stringFormatId = R.string.statistic_user_training_max_base_record_text_format
    val baseRecord = trainingMax.correspondingBaseRecord
    val baseRecordWeights = baseRecord.baseWeights
    val approximatedWeightsString: String = when(baseRecordWeights % 1.0) {
        in 0.0 .. 0.1 -> baseRecordWeights.toInt()
        else -> baseRecordWeights
    }.toString()

    text = resources.getString(stringFormatId, approximatedWeightsString, baseRecord.baseRepetitions, "kg")
}

@BindingAdapter("statisticUserTrainingMaxAchievedDate")
fun TextView.bindUserTrainingMaxAchievedDate(trainingMax: TrainingMax?) {
    trainingMax ?: return

    val achievedDate = trainingMax.lastUpdatedAt.toLocalDateTime()

    text = achievedDate.toFormattedString()
}



package kr.valor.juggernaut.ui.home.detail

import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.databinding.ItemDetailSessionHeaderBinding
import kr.valor.juggernaut.databinding.LayoutUserProgressionIndicatorBinding
import kr.valor.juggernaut.domain.progression.model.UserProgression

@BindingAdapter("detailedSessions")
fun RecyclerView.bindSessions(uiState: DetailUiState) {
    bindUiState(uiState) { uiResult ->
        val detailViewHolderHeaderItem =
            listOf(DetailViewHolderItem.HeaderItem(uiResult.userProgression))
        val detailViewHolderContentItems =
            uiResult.sessionSummaries.map { sessionSummary ->
                DetailViewHolderItem.ContentItem(sessionSummary = sessionSummary)
            }

        (adapter as DetailAdapter).submitList(detailViewHolderHeaderItem + detailViewHolderContentItems)
    }
}

@BindingAdapter("bindUserProgressionIndicator")
fun ConstraintLayout.bindUserProgression(userProgression: UserProgression?) {
    userProgression ?: return
    val binding =
        DataBindingUtil.findBinding<LayoutUserProgressionIndicatorBinding>(this) ?: return
    val containerBinding =
        DataBindingUtil.findBinding<ItemDetailSessionHeaderBinding>(this.parent as View) ?: return

    when(binding) {
        containerBinding.userProgressionPhaseIndicator -> {
            binding.bindUserProgression(userProgression.phase)
        }
        containerBinding.userProgressionMicrocycleIndicator -> {
            binding.bindUserProgression(userProgression.microCycle)
        }
    }
}

private fun <T: Enum<T>> LayoutUserProgressionIndicatorBinding.bindUserProgression(progressionElement: T) {
    val indicatorTitleTextView = (root as ViewGroup).children.find { view ->
        view.id == R.id.progression_indicator_title_text
    } as TextView

    val indicatorItemIconImageView = (root as ViewGroup).children.filter { view ->
        view is ImageView
    }.map { it as ImageView }.toList()

    val indicatorItemTitleTextView = (root as ViewGroup).children.filter { view ->
        view is TextView && view.id != R.id.progression_indicator_title_text
    }.map { it as TextView }.toList()

    val indicatorLine = (root as ViewGroup).children.toList() - (indicatorItemIconImageView + indicatorItemTitleTextView + indicatorTitleTextView)

    val primaryColor = TypedValue().let { typedValue ->
        root.context.theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
        ContextCompat.getColor(root.context, typedValue.resourceId)
    }

    val indicatorItemTitleTextViewResId = when(progressionElement) {
        is Phase -> R.string.detail_session_progression_indicator_phase_title_text_format
        is MicroCycle -> R.string.detail_session_progression_indicator_microcycle_title_text_format
        else -> throw IllegalArgumentException()
    }

    indicatorTitleTextView.text = root.resources.getString(indicatorItemTitleTextViewResId, progressionElement.name)

    indicatorItemTitleTextView.forEachIndexed { index, textView ->
        textView.text = when(progressionElement) {
            is Phase -> Phase.values()[index].baseAmrapRepetitions.toString()
            is MicroCycle -> MicroCycle.values()[index].abbreviatedName
            else -> throw IllegalArgumentException()
        }
    }

    (0..progressionElement.ordinal).forEach { index ->
        indicatorItemIconImageView[index].apply {
            setColorFilter(primaryColor)
            setImageResource(R.drawable.ic_progression_in_progress_24)
        }
        indicatorItemTitleTextView[index].setTextColor(primaryColor)
        if (index < progressionElement.ordinal) {
            indicatorLine[index].backgroundTintList = ColorStateList.valueOf(primaryColor)
        }
    }
}

private inline fun bindUiState(uiState: DetailUiState, block: (DetailUiState.Result) -> Unit) {
    if (uiState !is DetailUiState.Result) {
        return
    } else {
        block(uiState)
    }
}
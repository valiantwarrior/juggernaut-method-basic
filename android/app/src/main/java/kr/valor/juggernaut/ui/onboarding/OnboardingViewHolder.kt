package kr.valor.juggernaut.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kr.valor.juggernaut.R
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.databinding.ItemOnboardingFooterBinding
import kr.valor.juggernaut.databinding.ItemOnboardingFormBinding
import kr.valor.juggernaut.databinding.ItemOnboardingHeaderBinding
import kr.valor.juggernaut.ui.ViewHolderDataBindingFactory

sealed class OnboardingViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

    abstract fun bindViewHolder(position: Int)

}

class OnboardingFormViewHolder private constructor(
    private val binding: ItemOnboardingFormBinding
): OnboardingViewHolder(binding) {

    override fun bindViewHolder(position: Int) {
        with(binding) {
            currentPosition = position
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(
            parent: ViewGroup,
            inputWeightsStateFlow: MutableStateFlow<String>,
            inputRepetitionsStateFlow: MutableStateFlow<String>,
            submitClickListener: SubmitClickListener,
            backClickListener: BackClickListener
        ): OnboardingViewHolder {
            val binding =
                provideDataBinding<ItemOnboardingFormBinding>(parent, R.layout.item_onboarding_form)
                    .apply {
                        inputWeightsState = inputWeightsStateFlow
                        inputRepetitionsState = inputRepetitionsStateFlow
                        submitAction = submitClickListener
                        backAction = backClickListener
                    }

            return OnboardingFormViewHolder(binding)
        }
    }

}

class OnboardingFooterViewHolder private constructor(
    private val binding: ItemOnboardingFooterBinding
): OnboardingViewHolder(binding) {

    override fun bindViewHolder(position: Int) {
        with(binding) {
            currentPosition = position
            executePendingBindings()
        }
    }

    companion object: ViewHolderDataBindingFactory() {
        fun create(
            parent: ViewGroup,
            inputStateFlow: StateFlow<Map<LiftCategory, OnboardingUiModel>>,
            viewLifecycleOwner: LifecycleOwner,
            submitClickListener: SubmitClickListener,
            backClickListener: BackClickListener
        ): OnboardingViewHolder {
            val binding =
                provideDataBinding<ItemOnboardingFooterBinding>(parent, R.layout.item_onboarding_footer)
                    .apply {
                        submitAction = submitClickListener
                        backAction = backClickListener
                        inputState = inputStateFlow
                        lifecycleOwner = viewLifecycleOwner
                    }

            return OnboardingFooterViewHolder(binding)
        }
    }
}

class OnboardingHeaderViewHolder(
    binding: ItemOnboardingHeaderBinding
): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup, startAction: () -> Unit): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemOnboardingHeaderBinding.inflate(layoutInflater, parent, false)
                .apply {
                    startNewMethodButton.setOnClickListener {
                        startAction()
                    }
                }

            return OnboardingHeaderViewHolder(binding)
        }
    }
}
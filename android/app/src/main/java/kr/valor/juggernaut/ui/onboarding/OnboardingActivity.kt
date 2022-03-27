package kr.valor.juggernaut.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.valor.juggernaut.databinding.ActivityOnboardingBinding
import kr.valor.juggernaut.ui.MainActivity
import kr.valor.juggernaut.ui.observeFlowEvent

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
            .apply {
                initViewPager()
                initEventObserver()
            }

        setContentView(binding.root)
    }

    private fun ActivityOnboardingBinding.initViewPager() {
        val viewPagerAdapter = with(onboardingViewModel) {
            OnboardingPagerAdapter(
                this,
                lifecycleOwner = this@OnboardingActivity,
                startAction = {
                    onboardingPager.currentItem += 1
                },
                submitClickListener = SubmitClickListener { pagePosition ->
                    when(pagePosition) {
                        OnboardingPagerAdapter.FOOTER_PAGE_POSITION -> onClickComplete()
                        else -> onClickSubmit(pagePosition)
                    }
                },
                backClickListener = BackClickListener { pagePosition ->
                    val previousPagePosition = pagePosition - 1
                    onClickBack(previousPagePosition)
                }
            )
        }

        onboardingPager.isUserInputEnabled = false
        onboardingPager.adapter = viewPagerAdapter
    }

    private fun ActivityOnboardingBinding.initEventObserver() {
        observeFlowEvent(onboardingViewModel.uiEventFlow) { event ->
            when(event) {
                is OnboardingUiEvent.Next -> onboardingPager.currentItem = event.nextPagePosition
                is OnboardingUiEvent.Previous -> onboardingPager.currentItem = event.previousPagePosition
                OnboardingUiEvent.Done -> {
                    root.visibility = View.GONE
                    startActivity(
                        Intent(this@OnboardingActivity, MainActivity::class.java)
                    ).also { finish() }
                }
            }
        }
    }

}

class SubmitClickListener(private val clickListener: (Int) -> Unit) {
    fun onClick(pagePosition: Int) = clickListener(pagePosition)
}

class BackClickListener(private val clickListener: (Int) -> Unit) {
    fun onClick(pagePosition: Int) = clickListener(pagePosition)
}
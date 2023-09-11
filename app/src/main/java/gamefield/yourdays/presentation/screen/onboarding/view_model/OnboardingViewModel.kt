package gamefield.yourdays.presentation.screen.onboarding.view_model

import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gamefield.yourdays.extensions.toImmutable

class OnboardingViewModel : ViewModel() {

    private val _changeScreenEvent = MutableLiveData<OnboardingState>()
    val changeScreenEvent = _changeScreenEvent.toImmutable()

    private val _clearPreviousScreen = MutableLiveData<Boolean>()
    val clearPreviousScreen = _clearPreviousScreen.toImmutable()

    private val _sectionAlphaChanged = MutableLiveData<OnboardingSectionAnimationData>()
    val sectionAlphaChanged = _sectionAlphaChanged.toImmutable()

    private val _nextButtonEnabledEvent = MutableLiveData<Boolean>()
    val nextButtonEnabledEvent = _nextButtonEnabledEvent.toImmutable()

    init {
        _nextButtonEnabledEvent.value = false
        _changeScreenEvent.postValue(OnboardingState.FirstScreenState())
        observeScreenChanged()
    }

    private fun observeScreenChanged() {
        changeScreenEvent.observeForever { state ->
            startAnimation(
                delay = state.firstSectionAppearance,
                state = OnboardingSectionsAnimationState.FIRST_SECTION
            )
            startAnimation(
                delay = state.secondSectionAppearance,
                state = OnboardingSectionsAnimationState.SECOND_SECTION
            )
            startAnimation(
                delay = state.thirdSectionAppearance,
                state = OnboardingSectionsAnimationState.THIRD_SECTION
            )
            startAnimation(
                delay = state.nextButtonAppearance,
                state = OnboardingSectionsAnimationState.NEXT_BUTTON_SECTION,
                onAnimationEndAction = { _nextButtonEnabledEvent.postValue(true) }
            )
        }
    }

    private fun startAnimation(
        delay: Long,
        state: OnboardingSectionsAnimationState,
        onAnimationEndAction: (() -> Unit)? = null
    ) {
        ValueAnimator
            .ofFloat(START_ALPHA, END_ALPHA)
            .setDuration(APPEAR_DURATION).apply {
                startDelay = delay
                addUpdateListener { alpha ->
                    _sectionAlphaChanged.postValue(
                        OnboardingSectionAnimationData(
                            alpha = alpha.animatedValue as Float,
                            state = state
                        )
                    )
                }
                doOnEnd {
                    onAnimationEndAction?.invoke()
                }
            }
            .start()
    }

    fun onNextButtonClicked() {
        _nextButtonEnabledEvent.postValue(false)
        _clearPreviousScreen.postValue(true)
        when (_changeScreenEvent.value) {
            is OnboardingState.FirstScreenState -> {
                _changeScreenEvent.postValue(OnboardingState.SecondScreenState())
            }

            is OnboardingState.SecondScreenState -> {
                _changeScreenEvent.postValue(OnboardingState.ThirdScreenState())
            }

            is OnboardingState.ThirdScreenState -> {
                _changeScreenEvent.postValue(OnboardingState.GoToMainScreenState())
            }

            else -> {}
        }
    }

    private companion object {
        const val START_ALPHA = 0f
        const val END_ALPHA = 1f
        const val APPEAR_DURATION = 2000L
    }
}

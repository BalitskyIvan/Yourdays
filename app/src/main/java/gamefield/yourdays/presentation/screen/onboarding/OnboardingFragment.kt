package gamefield.yourdays.presentation.screen.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.presentation.Navigation
import gamefield.yourdays.databinding.FragmentOnboardingBinding
import gamefield.yourdays.presentation.screen.onboarding.view_model.OnboardingSectionsAnimationState
import gamefield.yourdays.presentation.screen.onboarding.view_model.OnboardingState
import gamefield.yourdays.presentation.screen.onboarding.view_model.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment : Fragment() {

    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentOnboardingBinding
    private val viewModel: OnboardingViewModel by viewModel()

    private var firstSectionViews = arrayListOf<View>()
    private var secondSectionViews = arrayListOf<View>()
    private var thirdSectionViews = arrayListOf<View>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation = requireActivity() as Navigation

        binding.buttonNext.setOnClickListener {
            viewModel.onNextButtonClicked()
        }
        observeClearScreen()
        observeScreenChanged()
        observeAlphaChanged()
        observeNextButtonEnabled()
    }

    private fun observeNextButtonEnabled() {
        viewModel.nextButtonEnabledEvent.observe(viewLifecycleOwner) {
            binding.buttonNext.isEnabled = it
        }
    }

    private fun observeClearScreen() {
        viewModel.clearPreviousScreen.observe(viewLifecycleOwner) {
            firstSectionViews.apply {
                setVisible(false)
                clear()
            }
            secondSectionViews.apply {
                setVisible(false)
                clear()
            }
            thirdSectionViews.apply {
                setVisible(false)
                clear()
            }
            binding.buttonNext.visibility = View.GONE
        }
    }

    private fun observeScreenChanged() {
        viewModel.changeScreenEvent.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is OnboardingState.FirstScreenState -> initFirstScreenSections(screenState.nextButtonText)
                is OnboardingState.SecondScreenState -> initSecondScreenSections(screenState.nextButtonText)
                is OnboardingState.ThirdScreenState -> initThirdScreenSections(screenState.nextButtonText)
                is OnboardingState.GoToMainScreenState -> {
                    navigation.goBackFromOnboarding()
                }
            }
        }
    }

    private fun observeAlphaChanged() {
        viewModel.sectionAlphaChanged.observe(viewLifecycleOwner) { animationData ->
            when (animationData.state) {
                OnboardingSectionsAnimationState.FIRST_SECTION ->
                    firstSectionViews.changeAlpha(animationData.alpha)

                OnboardingSectionsAnimationState.SECOND_SECTION ->
                    secondSectionViews.changeAlpha(animationData.alpha)

                OnboardingSectionsAnimationState.THIRD_SECTION ->
                    thirdSectionViews.changeAlpha(animationData.alpha)

                OnboardingSectionsAnimationState.NEXT_BUTTON_SECTION ->
                    binding.buttonNext.alpha = animationData.alpha
            }
        }
    }

    private fun initFirstScreenSections(@StringRes nextButtonText: Int) {
        with(binding) {
            firstSectionViews = arrayListOf(onboardingHelloMessage, onboardingLogo)
            secondSectionViews = arrayListOf(onboardingAppDescription)
            thirdSectionViews = arrayListOf(
                onboardingEmotionsShapesDescription,
                onboardingPlusShape,
                onboardingZeroShape,
                onboardingMinusShape,
                onboardingPlusShapeDescription,
                onboardingMinusShapeDescription,
                onboardingZeroShapeDescription
            )
            buttonNext.setButtonText(getString(nextButtonText))
        }
        setSectionsVisible()
    }

    private fun initSecondScreenSections(@StringRes nextButtonText: Int) {
        with(binding) {
            firstSectionViews = arrayListOf(
                onboardingEmotionDescriptionTitle,
                onboardingWorryProgressTitle,
                onboardingHappinessProgressTitle,
                onboardingSadnessProgressTitle,
                onboardingProductivityProgressTitle,
                onboardingWorryProgress,
                onboardingHappinessProgress,
                onboardingSadnessProgress,
                onboardingProductivityProgress,
            )
            secondSectionViews = arrayListOf(
                onboardingMonthPreviewTitle,
                onboardingMonthPreview
            )
            thirdSectionViews = arrayListOf(
                onboardingEmptyAnimationTitle,
                onboardingEmptyAnimation
            )
            buttonNext.setButtonText(getString(nextButtonText))
        }
        setSectionsVisible()
    }

    private fun initThirdScreenSections(@StringRes nextButtonText: Int) {
        with(binding) {
            firstSectionViews = arrayListOf(
                onboardingShareToInstagramTitle
            )
            secondSectionViews = arrayListOf(
                onboardingShareToInstagramPreview
            )
            thirdSectionViews = arrayListOf()
            buttonNext.setButtonText(getString(nextButtonText))
        }
        setSectionsVisible()
    }

    private fun setSectionsVisible() {
        binding.buttonNext.alpha = 0f
        binding.buttonNext.visibility = View.VISIBLE
        firstSectionViews.apply {
            changeAlpha(0f)
            setVisible(true)
        }
        secondSectionViews.apply {
            changeAlpha(0f)
            setVisible(true)
        }
        thirdSectionViews.apply {
            changeAlpha(0f)
            setVisible(true)
        }
    }

    private fun ArrayList<View>.changeAlpha(alpha: Float) {
        forEach { view ->
            view.alpha = alpha
        }
    }

    private fun ArrayList<View>.setVisible(visible: Boolean) {
        forEach { view ->
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

}

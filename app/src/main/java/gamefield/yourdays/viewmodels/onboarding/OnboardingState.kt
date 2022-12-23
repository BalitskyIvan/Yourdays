package gamefield.yourdays.viewmodels.onboarding

import androidx.annotation.StringRes
import gamefield.yourdays.R

sealed class OnboardingState(
    @StringRes
    val nextButtonText: Int,
    val firstSectionAppearance: Long,
    val secondSectionAppearance: Long,
    val thirdSectionAppearance: Long,
    val nextButtonAppearance: Long
    ) {

    private companion object {
        const val FIRST_SECTION_APPEARANCE = 1200L
        const val SECOND_SECTION_APPEARANCE = 4000L
        const val THIRD_SECTION_APPEARANCE = 8000L
        const val NEXT_BUTTON_APPEARANCE = 10500L
    }

    class FirstScreenState: OnboardingState(
        nextButtonText = R.string.next_button_first_screen_text,
        firstSectionAppearance = FIRST_SECTION_APPEARANCE,
        secondSectionAppearance = SECOND_SECTION_APPEARANCE,
        thirdSectionAppearance = THIRD_SECTION_APPEARANCE,
        nextButtonAppearance = NEXT_BUTTON_APPEARANCE
    )

    class SecondScreenState: OnboardingState(
        nextButtonText = R.string.next_button_second_screen_text,
        firstSectionAppearance = FIRST_SECTION_APPEARANCE,
        secondSectionAppearance = 6000,
        thirdSectionAppearance = 10000,
        nextButtonAppearance = 12000L
    )

    class ThirdScreenState: OnboardingState(
        nextButtonText = R.string.next_button_third_screen_text,
        firstSectionAppearance = FIRST_SECTION_APPEARANCE,
        secondSectionAppearance = 6000,
        thirdSectionAppearance = 0L,
        nextButtonAppearance = 10000L
    )

    class GoToMainScreenState: OnboardingState(
        nextButtonText = R.string.next_button_third_screen_text,
        firstSectionAppearance = 0L,
        secondSectionAppearance = 0L,
        thirdSectionAppearance = 0L,
        nextButtonAppearance = 0L
    )
}

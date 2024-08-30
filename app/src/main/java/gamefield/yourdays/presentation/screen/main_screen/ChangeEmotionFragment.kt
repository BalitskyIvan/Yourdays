package gamefield.yourdays.presentation.screen.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gamefield.yourdays.databinding.FragmentChangeEmotionBinding
import gamefield.yourdays.presentation.components.animation.ChangeEmotionOpeCloseAnimation
import gamefield.yourdays.extensions.setOnRippleClickListener
import gamefield.yourdays.presentation.screen.main_screen.utils.EmotionSeekBarListener
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ChangeEmotionFragment : Fragment() {

    private lateinit var binding: FragmentChangeEmotionBinding
    private val viewModel: MainScreenViewModel by activityViewModel()

    private lateinit var changeEmotionOpeCloseAnimation: ChangeEmotionOpeCloseAnimation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeEmotionBinding.inflate(inflater, container, false)
        changeEmotionOpeCloseAnimation = ChangeEmotionOpeCloseAnimation(
            resources = resources,
            rootLayout = binding.root,
            animationEndAction = viewModel::onChangeEmotionContainerOpenCloseAnimationEnd
        )

        binding.root.setOnClickListener {
            viewModel.onChangeContainerClicked()
        }

        setSeekBarListeners()
        setButtonsListeners()
        observeEmotionActions()
        observeEmotionMutableChanged()
        return binding.root
    }

    private fun observeEmotionActions() {
        with(viewModel) {
            changeEmotionFragmentOpeCloseAction.observe(viewLifecycleOwner) { openCloseActionData ->
                openCloseActionData?.let { changeEmotionOpeCloseAnimation.start() }
            }
            worryEmotionChangedEvent.observe(viewLifecycleOwner) { worry ->
                binding.worryScore.text = EMOTION_PROGRESS.format(worry / 10)
                binding.worry.progress = worry
            }
            happinessEmotionChangedEvent.observe(viewLifecycleOwner) { happiness ->
                binding.happinessScore.text = EMOTION_PROGRESS.format(happiness / 10)
                binding.happiness.progress = happiness
            }
            sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
                binding.sadnessScore.text = EMOTION_PROGRESS.format(sadness / 10)
                binding.sadness.progress = sadness
            }
            productivityEmotionChangedEvent.observe(viewLifecycleOwner) { productivity ->
                binding.productivityScore.text = EMOTION_PROGRESS.format(productivity / 10)
                binding.productivity.progress = productivity
            }
        }
    }

    private fun setSeekBarListeners() {
        with(binding) {
            worry.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::worryChanged))
            happiness.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::happinessChanged))
            sadness.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::sadnessChanged))
            productivity.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::productivityChanged))
        }
    }

    private fun observeEmotionMutableChanged() {
        viewModel.isDayMutableChangedEvent.observe(viewLifecycleOwner) { isMutable ->
            with(binding) {
                root.focusable = if (isMutable) View.NOT_FOCUSABLE else View.FOCUSABLE
                root.isEnabled = !isMutable

                worry.isEnabled = isMutable
                happiness.isEnabled = isMutable
                sadness.isEnabled = isMutable
                productivity.isEnabled = isMutable
            }
        }
    }

    private fun setButtonsListeners() {
        with(binding) {
            changeEmotionFragmentOkButton.setOnRippleClickListener(viewModel::emotionContainerOkButtonClicked)
            exportDayToInstagram.setOnRippleClickListener {
                viewModel.onExportToInstagramClicked(isExportDay = true)
            }
        }
    }

    private companion object {
        const val EMOTION_PROGRESS = "%s/10"
    }
}
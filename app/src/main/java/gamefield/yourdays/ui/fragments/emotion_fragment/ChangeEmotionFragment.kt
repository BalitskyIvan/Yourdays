package gamefield.yourdays.ui.fragments.emotion_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentChangeEmotionBinding
import gamefield.yourdays.utils.animation.ChangeEmotionOpeCloseAnimation
import gamefield.yourdays.extensions.setOnRippleClickListener
import gamefield.yourdays.utils.EmotionSeekBarListener
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class ChangeEmotionFragment : Fragment() {

    private lateinit var binding: FragmentChangeEmotionBinding
    private lateinit var viewModel: MainScreenFragmentViewModel

    private lateinit var changeEmotionOpeCloseAnimation: ChangeEmotionOpeCloseAnimation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity()).get(MainScreenFragmentViewModel::class.java)
        binding = FragmentChangeEmotionBinding.inflate(inflater, container, false)
        changeEmotionOpeCloseAnimation = ChangeEmotionOpeCloseAnimation(
            resources = resources,
            rootLayout = binding.root,
            animationEndAction = viewModel::onChangeEmotionContainerOpenCloseAnimationEnd
        )

        binding.root.focusable = View.FOCUSABLE
        binding.root.isEnabled = true

        binding.root.setOnClickListener {
            viewModel.onChangeContainerClicked()
        }

        setSeekBarListeners()
        setOkButtonListener()
        observeEmotionActions()
        observeEmotionMutableChanged()
        return binding.root
    }

    private fun observeEmotionActions() {
        viewModel.changeEmotionFragmentOpeCloseAction.observe(viewLifecycleOwner) { openCloseActionData ->
            changeEmotionOpeCloseAnimation.start()
        }

        viewModel.worryEmotionChangedEvent.observe(viewLifecycleOwner) { worry ->
            binding.worryScore.text = EMOTION_PROGRESS.format(worry / 10)
            binding.worry.progress = worry
        }
        viewModel.happinessEmotionChangedEvent.observe(viewLifecycleOwner) { happiness ->
            binding.happinessScore.text = EMOTION_PROGRESS.format(happiness / 10)
            binding.happiness.progress = happiness
        }
        viewModel.sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
            binding.sadnessScore.text = EMOTION_PROGRESS.format(sadness / 10)
            binding.sadness.progress = sadness
        }
        viewModel.productivityEmotionChangedEvent.observe(viewLifecycleOwner) { productivity ->
            binding.productivityScore.text = EMOTION_PROGRESS.format(productivity / 10)
            binding.productivity.progress = productivity
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
                worry.isEnabled = isMutable
                happiness.isEnabled = isMutable
                sadness.isEnabled = isMutable
                productivity.isEnabled = isMutable
            }
        }
    }

    private fun setOkButtonListener() {
        binding.changeEmotionFragmentOkButton.setOnRippleClickListener(viewModel::emotionContainerOkButtonClicked)
    }

    companion object {
        private const val EMOTION_PROGRESS = "%s/10"

        @JvmStatic
        fun newInstance() = ChangeEmotionFragment()
    }
}
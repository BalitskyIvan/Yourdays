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

        viewModel.anxietyEmotionChangedEvent.observe(viewLifecycleOwner) { anxiety ->
            binding.anxietyScore.text = EMOTION_PROGRESS.format(anxiety / 10)
            binding.anxiety.progress = anxiety
        }
        viewModel.joyEmotionChangedEvent.observe(viewLifecycleOwner) { joy ->
            binding.joyScore.text = EMOTION_PROGRESS.format(joy / 10)
            binding.joy.progress = joy
        }
        viewModel.sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
            binding.sadnessScore.text = EMOTION_PROGRESS.format(sadness / 10)
            binding.sadness.progress = sadness
        }
        viewModel.calmnessEmotionChangedEvent.observe(viewLifecycleOwner) { calmness ->
            binding.calmnessScore.text = EMOTION_PROGRESS.format(calmness / 10)
            binding.calmness.progress = calmness
        }
    }

    private fun setSeekBarListeners() {
        with(binding) {
            anxiety.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::anxietyChanged))
            joy.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::joyChanged))
            sadness.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::sadnessChanged))
            calmness.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::calmnessChanged))
        }
    }

    private fun observeEmotionMutableChanged() {
        viewModel.isDayMutableChangedEvent.observe(viewLifecycleOwner) { isMutable ->
            with(binding) {
                anxiety.isEnabled = isMutable
                joy.isEnabled = isMutable
                sadness.isEnabled = isMutable
                calmness.isEnabled = isMutable
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
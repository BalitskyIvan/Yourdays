package gamefield.yourdays.ui.fragments.emotion_fragment

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentChangeEmotionBinding
import gamefield.yourdays.extensions.setOnRippleClickListener
import gamefield.yourdays.utils.EmotionSeekBarListener
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class ChangeEmotionFragment : Fragment() {

    private lateinit var binding: FragmentChangeEmotionBinding
    private lateinit var viewModel: MainScreenFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity()).get(MainScreenFragmentViewModel::class.java)
        binding = FragmentChangeEmotionBinding.inflate(inflater, container, false)

        setSeekBarListeners()
        setOkButtonListener()
        observeEmotionActions()
        return binding.root
    }

    private fun observeEmotionActions() {
        viewModel.changeEmotionFragmentOpeCloseAction.observe(viewLifecycleOwner) { isOpenAction ->
            animateOpenClose(isOpenAction = isOpenAction)
        }

        viewModel.anxietyEmotionChangedEvent.observe(viewLifecycleOwner) { anxiety ->
            binding.anxietyScore.text = (anxiety / 10).toString() + "/10"
        }
        viewModel.joyEmotionChangedEvent.observe(viewLifecycleOwner) { joy ->
            binding.joyScore.text = (joy / 10).toString() + "/10"
        }
        viewModel.sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
            binding.sadnessScore.text = (sadness / 10).toString() + "/10"
        }
        viewModel.calmnessEmotionChangedEvent.observe(viewLifecycleOwner) { calmness ->
            binding.calmnessScore.text = (calmness / 10).toString() + "/10"
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

    private fun setOkButtonListener() {
        binding.changeEmotionFragmentOkButton.setOnRippleClickListener(viewModel::emotionContainerOkButtonClicked)
    }

    private fun animateOpenClose(isOpenAction: Boolean) {
        var startHeight = 0
        var endHeight =
            requireContext().resources.getDimension(R.dimen.change_emotion_container_height)
                .toInt()
        var startAlpha = 0f
        var endAlpha = 1f
        var alphaDuration = 900L

        if (!isOpenAction) {
            startHeight = endHeight
            endHeight = 0
            startAlpha = endAlpha
            endAlpha = 0f
            alphaDuration = 300L
        }

        ValueAnimator
            .ofInt(startHeight, endHeight)
            .setDuration(600).apply {
                addUpdateListener {
                    binding.root.layoutParams.height = it.animatedValue as Int
                    binding.root.requestLayout()
                }
            }
            .start()
        ValueAnimator
            .ofFloat(startAlpha, endAlpha)
            .setDuration(alphaDuration)
            .apply {
                addUpdateListener {
                    binding.root.alpha = it.animatedValue as Float
                    binding.root.invalidate()
                }
            }
            .start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeEmotionFragment()
    }
}
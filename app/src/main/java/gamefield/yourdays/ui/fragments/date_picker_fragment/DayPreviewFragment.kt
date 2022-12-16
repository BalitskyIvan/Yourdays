package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentDayPreviewFragmentBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.parseEmotionInEmotionView
import gamefield.yourdays.ui.customviews.emotions.*
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class DayPreviewFragment : Fragment() {

    private lateinit var viewModel: ExportToInstagramViewModel
    private lateinit var binding: FragmentDayPreviewFragmentBinding

    private lateinit var emptyEmotionView: EmptyEmotionView
    private lateinit var plusEmotionView: PlusEmotionView
    private lateinit var minusEmotionView: MinusEmotionView
    private lateinit var zeroEmotionView: ZeroEmotionView

    private var currentEmotion: EmotionView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayPreviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)

        emptyEmotionView = EmptyEmotionView(context = requireContext(), forceLightenTheme = true)
        plusEmotionView = PlusEmotionView(context = requireContext(), forceLightenTheme = true)
        minusEmotionView = MinusEmotionView(context = requireContext(), forceLightenTheme = true)
        zeroEmotionView = ZeroEmotionView(context = requireContext(), forceLightenTheme = true)

        binding.dayPreviewEmotionContainer.addView(emptyEmotionView)
        observeDayChangedEvent()
    }

    private fun observeDayChangedEvent() {
        viewModel.currentDayInPreviewChanged.observe(viewLifecycleOwner) { data ->
            with(binding) {
                dayPreviewDateText.text = data.first

                dayPreviewWorryProgress.isEnabled = false
                dayPreviewHappinessProgress.isEnabled = false
                dayPreviewSadnessProgress.isEnabled = false
                dayPreviewProductivityProgress.isEnabled = false

                currentEmotion = if (data.second == null) {
                    null
                } else {

                    when (data.second!!.type) {
                        EmotionType.NONE -> null
                        EmotionType.PLUS -> plusEmotionView
                        EmotionType.MINUS -> minusEmotionView
                        EmotionType.ZERO -> zeroEmotionView
                    }
                }

                if (currentEmotion != null) {
                    with(currentEmotion!!) {
                        parseEmotionInEmotionView(data.second!!)

                        dayPreviewWorryScore.text =
                            EMOTION_PROGRESS.format(worry / 10)
                        dayPreviewHappinessScore.text =
                            EMOTION_PROGRESS.format(happiness / 10)
                        dayPreviewSadnessScore.text =
                            EMOTION_PROGRESS.format(sadness / 10)
                        dayPreviewProductivityScore.text =
                            EMOTION_PROGRESS.format(productivity / 10)

                        dayPreviewWorryProgress.progress = worry
                        dayPreviewHappinessProgress.progress = happiness
                        dayPreviewSadnessProgress.progress = sadness
                        dayPreviewProductivityProgress.progress = productivity
                    }
                } else {
                    dayPreviewWorryScore.text = EMOTION_PROGRESS.format(0)
                    dayPreviewHappinessScore.text = EMOTION_PROGRESS.format(0)
                    dayPreviewSadnessScore.text = EMOTION_PROGRESS.format(0)
                    dayPreviewProductivityScore.text = EMOTION_PROGRESS.format(0)

                    dayPreviewWorryProgress.progress = 0
                    dayPreviewHappinessProgress.progress = 0
                    dayPreviewSadnessProgress.progress = 0
                    dayPreviewProductivityProgress.progress = 0
                }

                dayPreviewEmotionContainer.removeAllViews()
                dayPreviewEmotionContainer.addView(currentEmotion ?: emptyEmotionView)
            }
        }
    }

    companion object {
        private const val EMOTION_PROGRESS = "%s/10"

        @JvmStatic
        fun newInstance() = DayPreviewFragment()
    }
}
package gamefield.yourdays.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentMainScreenEmotionBinding
import gamefield.yourdays.ui.customviews.emotions.EmotionView
import gamefield.yourdays.ui.customviews.emotions.MinusEmotionView
import gamefield.yourdays.ui.customviews.emotions.PlusEmotionView
import gamefield.yourdays.ui.customviews.emotions.ZeroEmotionView
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class MainScreenEmotionFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenEmotionBinding
    private lateinit var viewModel: MainScreenFragmentViewModel
    private var currentEmotion: EmotionView? = null
    private var emotionContainer: FrameLayout? = null
    private var emotionContainerWidth: Int = 0
    private var emotionContainerHeight: Int = 0
    private lateinit var minusEmotionView: MinusEmotionView
    private lateinit var plusEmotionView: PlusEmotionView
    private lateinit var zeroEmotionView: ZeroEmotionView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenEmotionBinding.inflate(inflater, container, false)
        initEmotions()
        initEmotionContainer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())
            .get(MainScreenFragmentViewModel::class.java)
        observeEmotionChanges()
        observeEmotionsPeriodScrolled()
    }

    private fun initEmotions() {
        minusEmotionView = MinusEmotionView(requireContext())
        plusEmotionView = PlusEmotionView(requireContext())
        zeroEmotionView = ZeroEmotionView(requireContext())
    }

    private fun initEmotionContainer() {
        currentEmotion = plusEmotionView
        emotionContainer = binding.emotionContainer

        with(emotionContainer!!) {
            addView(currentEmotion)
            layoutParams
            emotionContainerWidth = layoutParams.height
            emotionContainerHeight = layoutParams.width

            setOnClickListener {
                viewModel.onFillEmotionClicked()
            }
        }
    }

    private fun observeEmotionChanges() {
        viewModel.anxietyEmotionChangedEvent.observe(viewLifecycleOwner) { anxiety ->
            currentEmotion?.anxiety = anxiety
        }
        viewModel.joyEmotionChangedEvent.observe(viewLifecycleOwner) { joy ->
            currentEmotion?.joy = joy
        }
        viewModel.sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
            currentEmotion?.sadness = sadness
        }
        viewModel.calmnessEmotionChangedEvent.observe(viewLifecycleOwner) { calmness ->
            currentEmotion?.calmness = calmness
        }
    }

    private fun observeEmotionsPeriodScrolled() {
        viewModel.emotionsPeriodScrolled.observe(viewLifecycleOwner) { y ->
            if (y < 600) {
                emotionContainer?.layoutParams?.height = emotionContainerWidth - (y / 2)
                emotionContainer?.layoutParams?.width = emotionContainerHeight - (y / 2)

                emotionContainer?.requestLayout()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainScreenEmotionFragment()
    }
}

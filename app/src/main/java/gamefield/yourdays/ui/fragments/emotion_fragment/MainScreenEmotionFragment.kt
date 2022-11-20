package gamefield.yourdays.ui.fragments.emotion_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentMainScreenEmotionBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.ui.customviews.emotions.*
import gamefield.yourdays.utils.animation.DateTitleAnimation
import gamefield.yourdays.viewmodels.MainScreenFragmentEmotionViewModel
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class MainScreenEmotionFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenEmotionBinding
    private lateinit var mainScreenViewModel: MainScreenFragmentViewModel
    private lateinit var viewModel: MainScreenFragmentEmotionViewModel

    private lateinit var dateTitleAnimation: DateTitleAnimation

    private var currentEmotion: EmotionView? = null
    private var emotionContainer: FrameLayout? = null
    private var titleTextSize: Int = 0
    private var emotionContainerWidth: Int = 0
    private var emotionContainerHeight: Int = 0
    private lateinit var minusEmotionView: MinusEmotionView
    private lateinit var plusEmotionView: PlusEmotionView
    private lateinit var zeroEmotionView: ZeroEmotionView
    private var isAlreadyScrolled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenEmotionBinding.inflate(inflater, container, false)
        dateTitleAnimation = DateTitleAnimation(
            titleFirstView = binding.titeDate,
            titleSecondView = binding.tite
        )
        initEmotions()
        initEmotionContainer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainScreenViewModel = ViewModelProvider(requireActivity())
            .get(MainScreenFragmentViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity())
            .get(MainScreenFragmentEmotionViewModel::class.java)
        viewModel.initializeAction(context = view.context)

        observeEmotionChanges()
        observeEmotionsPeriodScrolled()
        observeAnimation()
        observeDateAndTitle()
        observeViewsVisibility()
    }

    private fun initEmotions() {
        minusEmotionView = MinusEmotionView(requireContext()).apply { isDrawStroke = true }
        plusEmotionView = PlusEmotionView(requireContext()).apply { isDrawStroke = true }
        zeroEmotionView = ZeroEmotionView(requireContext()).apply { isDrawStroke = true }
    }

    private fun initEmotionContainer() {
        currentEmotion = plusEmotionView
        emotionContainer = binding.emotionContainer
        with(emotionContainer!!) {
            addView(currentEmotion)
            layoutParams
            titleTextSize = binding.tite.layoutParams.height
            emotionContainerWidth = layoutParams.height
            emotionContainerHeight = layoutParams.width

            setOnClickListener {
                mainScreenViewModel.onFillEmotionClicked()
                viewModel.onEmotionClicked()
            }
        }
    }

    private fun observeEmotionChanges() {
        mainScreenViewModel.anxietyEmotionChangedEvent.observe(viewLifecycleOwner) { anxiety ->
            currentEmotion?.anxiety = anxiety
        }
        mainScreenViewModel.joyEmotionChangedEvent.observe(viewLifecycleOwner) { joy ->
            currentEmotion?.joy = joy
        }
        mainScreenViewModel.sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
            currentEmotion?.sadness = sadness
        }
        mainScreenViewModel.calmnessEmotionChangedEvent.observe(viewLifecycleOwner) { calmness ->
            currentEmotion?.calmness = calmness
        }
    }

    private fun observeEmotionsPeriodScrolled() {
        mainScreenViewModel.emotionsPeriodScrolled.observe(viewLifecycleOwner) { y ->
            if (y < 600) {
                isAlreadyScrolled = false
                emotionContainer?.layoutParams?.height = emotionContainerWidth - (y / 2)
                emotionContainer?.layoutParams?.width = emotionContainerHeight - (y / 2)
                binding.tite.layoutParams.height = titleTextSize - (y / 10)
                binding.tite.requestLayout()
                emotionContainer?.requestLayout()

            } else if (!isAlreadyScrolled) {
                isAlreadyScrolled = true
                emotionContainer?.layoutParams?.height = emotionContainerWidth - (600 / 2)
                emotionContainer?.layoutParams?.width = emotionContainerHeight - (600 / 2)
                binding.tite.layoutParams.height = titleTextSize - (y / 10)
                binding.tite.requestLayout()
                emotionContainer?.requestLayout()
            }
        }
    }

    private fun observeAnimation() {
        viewModel.currentEmotionType.observe(viewLifecycleOwner) { newEmotionType ->
            emotionContainer?.removeView(currentEmotion)
            val nextEmotion = when (newEmotionType) {
                EmotionType.PLUS -> plusEmotionView
                EmotionType.ZERO -> zeroEmotionView
                EmotionType.MINUS -> minusEmotionView
                else -> plusEmotionView
            }
            currentEmotion?.let { nextEmotion.copyEmotions(it) }
            currentEmotion = nextEmotion
            emotionContainer?.addView(currentEmotion)
            mainScreenViewModel.emotionTypeChanged(newEmotionType)
        }
        viewModel.emotionContainerAlpha.observe(viewLifecycleOwner) {
            emotionContainer?.alpha = it
        }
    }

    private fun observeViewsVisibility() {
        viewModel.changeClickToFillTextVisibility.observe(viewLifecycleOwner) { isVisible ->
            binding.clickToFillText.isVisible = isVisible
        }
        viewModel.changeFirstTitleVisibility.observe(viewLifecycleOwner) { isVisible ->
            binding.titeDate.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun observeDateAndTitle() {
        mainScreenViewModel.changeEmotionFragmentOpeCloseAction.observe(viewLifecycleOwner) { openCloseActionData ->
            viewModel.onOpenCloseChangingEmotionContained(data = openCloseActionData)
        }
        viewModel.changeDateWithTitle.observe(viewLifecycleOwner) { dateTitleAnimation.start() }
        viewModel.dateTitleChanged.observe(viewLifecycleOwner) { dateTitleAnimation.setDateTitle(newDate = it) }
        mainScreenViewModel.daySelectedEvent.observe(viewLifecycleOwner) {
            viewModel.onDayChanged(
                daySelectedContainer = it,
                context = requireContext()
            )
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainScreenEmotionFragment()
    }
}

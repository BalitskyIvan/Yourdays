package gamefield.yourdays.ui.fragments.emotion_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.Navigation
import gamefield.yourdays.R
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
    private var emotionContainerWidth: Int = 0
    private var emotionContainerHeight: Int = 0
    private lateinit var minusEmotionView: MinusEmotionView
    private lateinit var plusEmotionView: PlusEmotionView
    private lateinit var zeroEmotionView: ZeroEmotionView
    private var isAlreadyScrolled = false
    private lateinit var navigation: Navigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenEmotionBinding.inflate(inflater, container, false).apply {
            titeDate.y = resources.getDimension(R.dimen.top_position_y)
            tite.y = resources.getDimension(R.dimen.bottom_position_y)

            dateTitleAnimation = DateTitleAnimation(
                titleFirstView = titeDate,
                titleSecondView = tite,
                resources = resources
            )
        }

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
        navigation = requireActivity() as Navigation

        binding.uploadMonthToInstagramButton.setOnClickListener {
            mainScreenViewModel.onExportToInstagramClicked()
        }
        observeEmotionChanges()
        observeEmotionsPeriodScrolled()
        observeAnimation()
        observeDateAndTitle()
        observeViewsVisibility()
        observeEmotionMutableChanged()
        observeNavigateToInstagramScreen()
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
            emotionContainerWidth = layoutParams.height
            emotionContainerHeight = layoutParams.width

            setOnClickListener {
                mainScreenViewModel.onFillEmotionClicked()
                viewModel.onEmotionClicked()
            }
        }
    }

    private fun observeEmotionChanges() {
        mainScreenViewModel.worryEmotionChangedEvent.observe(viewLifecycleOwner) { worry ->
            currentEmotion?.worry = worry
        }
        mainScreenViewModel.happinessEmotionChangedEvent.observe(viewLifecycleOwner) { happiness ->
            currentEmotion?.happiness = happiness
        }
        mainScreenViewModel.sadnessEmotionChangedEvent.observe(viewLifecycleOwner) { sadness ->
            currentEmotion?.sadness = sadness
        }
        mainScreenViewModel.productivityEmotionChangedEvent.observe(viewLifecycleOwner) { productivity ->
            currentEmotion?.productivity = productivity
        }
    }

    private fun observeEmotionsPeriodScrolled() {
        mainScreenViewModel.emotionsPeriodScrolled.observe(viewLifecycleOwner) { y ->
            if (y < 600) {
                isAlreadyScrolled = false
                emotionContainer?.layoutParams?.height = emotionContainerWidth - (y / 2)
                emotionContainer?.layoutParams?.width = emotionContainerHeight - (y / 2)
                binding.tite.requestLayout()
                binding.titeDate.requestLayout()
                emotionContainer?.requestLayout()

            } else if (!isAlreadyScrolled) {
                isAlreadyScrolled = true
                emotionContainer?.layoutParams?.height = emotionContainerWidth - (600 / 2)
                emotionContainer?.layoutParams?.width = emotionContainerHeight - (600 / 2)
                binding.tite.requestLayout()
                binding.titeDate.requestLayout()
                emotionContainer?.requestLayout()
            }
        }
    }

    private fun observeAnimation() {
        viewModel.currentEmotionType.observe(viewLifecycleOwner) { newEmotionType ->
            val nextEmotion = when (newEmotionType) {
                EmotionType.PLUS -> plusEmotionView
                EmotionType.ZERO -> zeroEmotionView
                EmotionType.MINUS -> minusEmotionView
                else -> plusEmotionView
            }
            currentEmotion?.let { nextEmotion.copyEmotions(it) }
            emotionContainer?.removeView(currentEmotion)

            mainScreenViewModel.emotionTypeChanged(newEmotionType)
            currentEmotion = nextEmotion
            emotionContainer?.addView(currentEmotion)
        }
        viewModel.emotionContainerAlpha.observe(viewLifecycleOwner) {
            emotionContainer?.alpha = it
        }
    }

    private fun observeViewsVisibility() {
        viewModel.clickToChangeEmotionTextAlphaChangedEvent.observe(viewLifecycleOwner) { alpha ->
            binding.clickToChangeEmotionText.alpha = alpha
        }
        viewModel.clickToFillTextAlphaChangedEvent.observe(viewLifecycleOwner) { alpha ->
            binding.clickToFillText.alpha = alpha
        }
        viewModel.exportInstagramAlphaChangedEvent.observe(viewLifecycleOwner) { alpha ->
            binding.uploadMonthToInstagramButton.alpha = alpha
        }
    }

    private fun observeDateAndTitle() {
        mainScreenViewModel.changeEmotionFragmentOpeCloseAction.observe(viewLifecycleOwner) { openCloseActionData ->
            openCloseActionData?.let {
                viewModel.onOpenCloseChangingEmotionContained(data = openCloseActionData)
                if (openCloseActionData.isEmotionNotFilled)
                    setDrawStroke(true)
            }
        }
        viewModel.changeDateWithTitle.observe(viewLifecycleOwner) { data ->
            data?.let { dateTitleAnimation.start(animationState = data.first, isFirstViewDate = data.second) }
        }
        viewModel.dateTitleChanged.observe(viewLifecycleOwner) {
            dateTitleAnimation.setDateTitle(
                newDate = it
            )
        }
        mainScreenViewModel.daySelectedEvent.observe(viewLifecycleOwner) {
            viewModel.onDayChanged(
                daySelectedContainer = it,
                context = requireContext()
            )
        }
    }

    private fun observeEmotionMutableChanged() {
        mainScreenViewModel.isDayMutableChangedEvent.observe(viewLifecycleOwner) { isMutable ->
            setDrawStroke(isMutable)
            currentEmotion?.invalidate()
            viewModel.dayMutableChanged(isMutable = isMutable)
        }
    }

    private fun observeNavigateToInstagramScreen() {
        mainScreenViewModel.navigateToExportScreen.observe(viewLifecycleOwner) {
            it?.let { dateToExportData ->
                navigation.goToExportToInstagramScreen(dateToExportData)
                mainScreenViewModel.onNavigate()
            }
        }
    }

    private fun setDrawStroke(isDraw: Boolean) {
        plusEmotionView.isDrawStroke = isDraw
        minusEmotionView.isDrawStroke = isDraw
        zeroEmotionView.isDrawStroke = isDraw
        currentEmotion?.isDrawStroke = isDraw
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainScreenEmotionFragment()
    }
}

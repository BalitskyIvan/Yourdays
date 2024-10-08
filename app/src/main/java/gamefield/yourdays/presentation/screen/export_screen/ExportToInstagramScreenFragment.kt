package gamefield.yourdays.presentation.screen.export_screen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import gamefield.yourdays.presentation.Navigation
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentExportToInstagramScreenBinding
import gamefield.yourdays.extensions.setOnRippleClickListener
import gamefield.yourdays.presentation.components.alert_dialog.CommonErrorDialog
import gamefield.yourdays.presentation.screen.main_screen.DayPreviewFragment
import gamefield.yourdays.presentation.screen.main_screen.MonthPreviewFragment
import gamefield.yourdays.presentation.screen.export_screen.view_model.DatePickerType
import gamefield.yourdays.presentation.screen.export_screen.view_model.InstagramStoriesBackgroundColor
import gamefield.yourdays.presentation.screen.export_screen.view_model.OpenInstagramUtils
import gamefield.yourdays.presentation.screen.export_screen.view_model.PickedDateData
import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import org.koin.androidx.fragment.android.replace
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.util.Calendar

class ExportToInstagramScreenFragment : Fragment() {

    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentExportToInstagramScreenBinding
    private val viewModel: ExportToInstagramViewModel by activityViewModel()
    private lateinit var pickImageActivityResult: ActivityResultLauncher<PickVisualMediaRequest>

    private val calendar = Calendar.getInstance()

    private var initDate = PickedDateData(
        day = calendar.get(Calendar.DAY_OF_MONTH),
        month = calendar.get(Calendar.MONTH),
        year = calendar.get(Calendar.YEAR)
    )
    private var isExportDay: Boolean = false
    private lateinit var colorSelector: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initWithContext(resources = resources)
        pickImageActivityResult =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                viewModel.onBackgroundImagePicked(uri)
            }
        arguments?.let {
            initDate = PickedDateData(
                day = it.getInt(DAY_KEY),
                month = it.getInt(MONTH_KEY),
                year = it.getInt(YEAR_KEY)
            )
            isExportDay = it.getBoolean(IS_EXPORT_DAY_KEY)

            viewModel.initSelectedData(dateData = initDate, isExportDay = isExportDay)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExportToInstagramScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorSelector = requireContext().getDrawable(R.drawable.color_selctor)!!
        navigation = requireActivity() as Navigation

        initButtons()
        setColorSelectorsListeners()
        observeButtonSelected()
        observeOpenDialog()
        observeOpenInstagram()
        observeSelectedColorChanged()
        observeBackgroundImagePicked()
        observeShowErrorEvent()
        observeUploadPreviewEvent()
    }

    private fun initButtons() {
        binding.closeButton.setOnRippleClickListener {
            navigation.goBack()
        }
        binding.monthButton.setOnClickListener {
            viewModel.onMonthButtonClicked()
        }
        binding.dayButton.setOnClickListener {
            viewModel.onDayButtonClicked()
        }
        binding.uploadBackgroundButton.setOnClickListener {
            pickImageActivityResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        with(binding.uploadButton) {
            paint.shader = LinearGradient(
                0f,
                0f,
                layoutParams.width.toFloat(),
                0f,
                requireContext().getColor(R.color.instagram_gradient_start_color),
                requireContext().getColor(R.color.instagram_gradient_end_color),
                Shader.TileMode.MIRROR
            )
            setOnRippleClickListener {
                viewModel.onUploadButtonClicked()
            }
        }
    }

    private fun observeUploadPreviewEvent() {
        viewModel.uploadDayEvent.observe(viewLifecycleOwner) { event ->
            event?.let {
                val fragment = childFragmentManager.findFragmentByTag("DayPreviewFragment")

                if (fragment != null) {
                    with(fragment.requireView()) {
                        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(bitmap)
                        fragment.view?.draw(canvas)
                        viewModel.upload(bitmap)
                    }
                }
            }
        }
        viewModel.uploadMonthEvent.observe(viewLifecycleOwner) {
            it?.let {
                val fragment = childFragmentManager.findFragmentByTag("MonthPreviewFragment")

                if (fragment != null) {
                    with(fragment.requireView()) {
                        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(bitmap)
                        fragment.view?.draw(canvas)
                        viewModel.upload(bitmap)
                    }
                }
            }
        }
    }

    private fun observeButtonSelected() {
        viewModel.monthButtonAlphaChanged.observe(viewLifecycleOwner) { alpha ->
            binding.monthButton.alpha = alpha
        }
        viewModel.dayButtonAlphaChanged.observe(viewLifecycleOwner) { alpha ->
            binding.dayButton.alpha = alpha
        }
    }

    private fun observeOpenInstagram() {
        viewModel.openInstagramEvent.observe(viewLifecycleOwner) { openInstagramData ->
            openInstagramData?.let {
                if (!OpenInstagramUtils.open(openInstagramData, requireActivity()))
                    viewModel.onInstagramError()
            }
        }
    }

    private fun observeShowErrorEvent() {
        viewModel.showErrorAlertEvent.observe(viewLifecycleOwner) { errorType ->
            errorType?.let {
                CommonErrorDialog(errorType = errorType).show(childFragmentManager, "")
            }
        }
    }

    private fun observeBackgroundImagePicked() {
        viewModel.backgroundImagePickedEvent.observe(viewLifecycleOwner) { uri ->
            binding.uploadedImagePreview.setImageURI(uri)
        }
    }

    private fun observeOpenDialog() {
        viewModel.periodPickerChanged.observe(viewLifecycleOwner) { type ->
            childFragmentManager
                .beginTransaction()
                .apply {
                    when (type) {
                        DatePickerType.DAY -> replace<DayPreviewFragment>(
                            containerViewId = R.id.preview_container,
                            tag = "DayPreviewFragment"
                        )

                        DatePickerType.MONTH -> replace<MonthPreviewFragment>(
                            containerViewId = R.id.preview_container,
                            tag = "MonthPreviewFragment"
                        )
                    }
                }
                .commitNow()
            childFragmentManager
                .beginTransaction()
                .apply {
                    when (type) {
                        DatePickerType.DAY -> replace<DayPreviewFragment>(
                            containerViewId = R.id.preview_container,
                            tag = "DayPreviewFragment"
                        )

                        DatePickerType.MONTH -> replace<MonthPreviewFragment>(
                            containerViewId = R.id.preview_container,
                            tag = "MonthPreviewFragment"
                        )
                    }
                }
                .commitNow()
        }
    }

    private fun setColorSelectorsListeners() {
        with(binding) {
            whiteColorSelector.setOnClickListener { viewModel.colorSelected(color = InstagramStoriesBackgroundColor.WHITE) }
            purpleColorSelector.setOnClickListener { viewModel.colorSelected(color = InstagramStoriesBackgroundColor.PURPLE) }
            blueColorSelector.setOnClickListener { viewModel.colorSelected(color = InstagramStoriesBackgroundColor.BLUE) }
            orangeColorSelector.setOnClickListener { viewModel.colorSelected(color = InstagramStoriesBackgroundColor.ORANGE) }
            blackColorSelector.setOnClickListener { viewModel.colorSelected(color = InstagramStoriesBackgroundColor.BLACK) }
        }
    }

    private fun observeSelectedColorChanged() {
        viewModel.colorUnselectedEvent.observe(viewLifecycleOwner) { unselectedColor ->
            setColorSelectorsBackground(unselectedColor, null)
        }
        viewModel.colorSelectedEvent.observe(viewLifecycleOwner) { selectedColor ->
            setColorSelectorsBackground(selectedColor, colorSelector)
        }
    }

    private fun setColorSelectorsBackground(
        selectedColor: InstagramStoriesBackgroundColor,
        background: Drawable?
    ) {
        with(binding) {
            when (selectedColor) {
                InstagramStoriesBackgroundColor.WHITE -> whiteColorSelector.background = background
                InstagramStoriesBackgroundColor.PURPLE -> purpleColorSelector.background =
                    background

                InstagramStoriesBackgroundColor.BLUE -> blueColorSelector.background = background
                InstagramStoriesBackgroundColor.ORANGE -> orangeColorSelector.background =
                    background

                InstagramStoriesBackgroundColor.BLACK -> blackColorSelector.background = background
            }
        }
    }

    companion object {
        const val DAY_KEY = "DAY_KEY"
        const val MONTH_KEY = "MONTH_KEY"
        const val YEAR_KEY = "YEAR_KEY"
        const val IS_EXPORT_DAY_KEY = "IS_EXPORT_DAY_KEY"
    }
}
package gamefield.yourdays.ui.fragments.screens

import android.content.Intent
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
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.Navigation
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentExportToInstagramScreenBinding
import gamefield.yourdays.extensions.setOnRippleClickListener
import gamefield.yourdays.ui.fragments.date_picker_fragment.DayPickerFragment
import gamefield.yourdays.ui.fragments.date_picker_fragment.DayPreviewFragment
import gamefield.yourdays.ui.fragments.date_picker_fragment.MonthPickerFragment
import gamefield.yourdays.ui.fragments.date_picker_fragment.MonthPreviewFragment
import gamefield.yourdays.utils.emum.DatePickerType
import gamefield.yourdays.utils.export_screen.InstagramStoriesBackgroundColor
import gamefield.yourdays.utils.export_screen.PickedDateData
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel
import java.util.Calendar

class ExportToInstagramScreenFragment : Fragment() {

    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentExportToInstagramScreenBinding
    private lateinit var viewModel: ExportToInstagramViewModel
    private lateinit var pickImageActivityResult: ActivityResultLauncher<PickVisualMediaRequest>

    private val calendar = Calendar.getInstance()

    private var initDate = PickedDateData(
        day = calendar.get(Calendar.DAY_OF_MONTH),
        month = calendar.get(Calendar.MONTH),
        year = calendar.get(Calendar.YEAR)
    )

    private val monthPreviewFragment = MonthPreviewFragment.newInstance()
    private val dayPreviewFragment = DayPreviewFragment.newInstance()

    private lateinit var colorSelector: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickImageActivityResult = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.onBackgroundImagePicked(uri)
        }
        arguments?.let {
            initDate = PickedDateData(
                day = it.getInt(DAY_KEY),
                month = it.getInt(MONTH_KEY),
                year = it.getInt(YEAR_KEY)
            )
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
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)
        viewModel.initWithContext(context = requireContext())
        viewModel.initSelectedData(dateData = initDate)

        colorSelector = requireContext().getDrawable(R.drawable.color_selctor)!!
        navigation = requireActivity() as Navigation

        initButtons()
        setColorSelectorsListeners()
        observeButtonSelected()
        observeOpenDialog()
        observeOpenInstagram()
        observeSelectedColorChanged()
        observeBackgroundImagePicked()
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
                with(monthPreviewFragment.requireView()) {
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    monthPreviewFragment.view?.draw(canvas)
                    viewModel.onUploadButtonClicked(bitmap, requireContext())
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
        viewModel.openInstagramEvent.observe(viewLifecycleOwner) { uri ->
            Intent("com.instagram.share.ADD_TO_STORY").apply {
                putExtra("source_application", "124143913125566")
                type = "image/*"
                putExtra("interactive_asset_uri", uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                activity?.startActivityForResult(this, 0);
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
                .replace(
                    R.id.period_picker_container, when (type!!) {
                        DatePickerType.DAY -> DayPickerFragment.newInstance()
                        DatePickerType.MONTH -> MonthPickerFragment.newInstance()
                    }
                )
                .commitNow()
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.preview_container, when (type) {
                        DatePickerType.DAY -> dayPreviewFragment
                        DatePickerType.MONTH -> monthPreviewFragment
                    }
                )
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
                InstagramStoriesBackgroundColor.PURPLE -> purpleColorSelector.background = background
                InstagramStoriesBackgroundColor.BLUE -> blueColorSelector.background = background
                InstagramStoriesBackgroundColor.ORANGE -> orangeColorSelector.background = background
                InstagramStoriesBackgroundColor.BLACK -> blackColorSelector.background = background
            }
        }
    }

    companion object {
        private const val DAY_KEY = "DAY_KEY"
        private const val MONTH_KEY = "MONTH_KEY"
        private const val YEAR_KEY = "YEAR_KEY"

        @JvmStatic
        fun newInstance(day: Int, month: Int, year: Int) =
            ExportToInstagramScreenFragment().apply {
                arguments = Bundle().apply {
                    putInt(DAY_KEY, day)
                    putInt(MONTH_KEY, month)
                    putInt(YEAR_KEY, year)
                }
            }
    }
}
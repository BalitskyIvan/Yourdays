package gamefield.yourdays.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.Navigation
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentExportToInstagramScreenBinding
import gamefield.yourdays.extensions.setOnRippleClickListener
import gamefield.yourdays.ui.fragments.date_picker_fragment.DayPickerFragment
import gamefield.yourdays.ui.fragments.date_picker_fragment.MonthPickerFragment
import gamefield.yourdays.utils.emum.DatePickerType
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel
import java.util.Calendar

class ExportToInstagramScreenFragment : Fragment() {

    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentExportToInstagramScreenBinding
    private lateinit var viewModel: ExportToInstagramViewModel

    private val calendar = Calendar.getInstance()

    private var selectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
    private var selectedMonth: Int = calendar.get(Calendar.MONTH)
    private var selectedYear: Int = calendar.get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDay = it.getInt(DAY_KEY)
            selectedMonth = it.getInt(MONTH_KEY)
            selectedYear = it.getInt(YEAR_KEY)
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

        navigation = requireActivity() as Navigation
        binding.closeButton.setOnRippleClickListener {
            navigation.goBack()
        }
        binding.monthButton.setOnClickListener {
            viewModel.onMonthButtonClicked()
        }
        binding.dayButton.setOnClickListener {
            viewModel.onDayButtonClicked()
        }
        observeButtonSelected()
        observeOpenDialog()
    }

    private fun observeButtonSelected() {
        viewModel.monthButtonAlphaChanged.observe(viewLifecycleOwner) { alpha ->
            binding.monthButton.alpha = alpha
        }
        viewModel.dayButtonAlphaChanged.observe(viewLifecycleOwner) { alpha ->
            binding.dayButton.alpha = alpha
        }
    }

    private fun observeOpenDialog() {
        viewModel.periodPickerChanged.observe(viewLifecycleOwner) { type ->
            childFragmentManager
                .beginTransaction()
                .replace(R.id.period_picker_container, when (type!!) {
                    DatePickerType.DAY -> DayPickerFragment.newInstance()
                    DatePickerType.MONTH -> MonthPickerFragment.newInstance()
                })
                .commitNow()
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
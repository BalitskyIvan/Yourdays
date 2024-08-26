package gamefield.yourdays.presentation.screen.export_screen

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gamefield.yourdays.databinding.FragmentMonthPickerBinding
import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MonthPickerFragment : Fragment() {

    private lateinit var binding: FragmentMonthPickerBinding
    private val viewModel: ExportToInstagramViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePickersDataChanged()
        observeCurrentValueChanged()
        setPickerListeners()
    }

    private fun observePickersDataChanged() {
        viewModel.monthListInPickerChanged.observe(viewLifecycleOwner) { months ->
            binding.monthPicker.displayedValues = months.map { it.first }.toTypedArray()
            binding.monthPicker.minValue = 0
            binding.monthPicker.maxValue = months.size - 1
        }
        viewModel.yearsListInPickerChanged.observe(viewLifecycleOwner) { years ->
            binding.yearPicker.displayedValues = years.toTypedArray()
            binding.yearPicker.minValue = 0
            binding.yearPicker.maxValue = years.size - 1
        }
    }

    private fun observeCurrentValueChanged() {
        viewModel.monthValueInPickerChanged.observe(viewLifecycleOwner) { month ->
            binding.monthPicker.value = month
            onPickersChanged(viewModel::onMonthPickerChanged)
        }
        viewModel.yearsValueInPickerChanged.observe(viewLifecycleOwner) { years ->
            binding.yearPicker.value = years
            onPickersChanged(viewModel::onYearPickerChanged)
        }
    }

    private fun setPickerListeners() {
        binding.monthPicker.setOnValueChangedListener { _, _, _ ->
            onPickersChanged(viewModel::onMonthPickerChanged)
        }
        binding.yearPicker.setOnValueChangedListener { _, _, _ ->
            onPickersChanged(viewModel::onYearPickerChanged)
        }
    }

    private fun onPickersChanged(listener: (monthName: String, yearName: String, resources: Resources) -> Unit) {
        listener.invoke(
            binding.monthPicker.displayedValues[binding.monthPicker.value],
            binding.yearPicker.displayedValues[binding.yearPicker.value],
            resources
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = MonthPickerFragment()
    }

}

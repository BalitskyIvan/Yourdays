package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentMonthPickerBinding
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class MonthPickerFragment : Fragment() {

    private lateinit var binding: FragmentMonthPickerBinding
    private lateinit var viewModel: ExportToInstagramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)
        observePickersDataChanged()
        setPickerListeners()
    }

    private fun observePickersDataChanged() {
        viewModel.monthListInPickerChanged.observe(viewLifecycleOwner) { months ->
            binding.monthPicker.displayedValues = months.toTypedArray()
            binding.monthPicker.minValue = 0
            binding.monthPicker.maxValue = months.size - 1
        }
        viewModel.yearsListInPickerChanged.observe(viewLifecycleOwner) { years ->
            binding.yearPicker.displayedValues = years.toTypedArray()
            binding.yearPicker.minValue = 0
            binding.yearPicker.maxValue = years.size - 1
        }
    }

    private fun setPickerListeners() {
        binding.monthPicker.setOnValueChangedListener { _, _, _ ->
           onPickerChanged()
        }
        binding.yearPicker.setOnValueChangedListener { _, _, _ ->
            onPickerChanged()
        }
    }

    private fun onPickerChanged() {
        viewModel.onMonthPickerChanged(
            monthName = binding.monthPicker.displayedValues.get(binding.monthPicker.value),
            yearName = binding.yearPicker.displayedValues.get(binding.yearPicker.value),
            context = requireContext()
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = MonthPickerFragment()
    }

}

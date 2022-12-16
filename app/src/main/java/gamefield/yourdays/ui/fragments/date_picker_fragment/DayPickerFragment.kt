package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentDayPickerBinding
import gamefield.yourdays.utils.export_screen.PickedDateData
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class DayPickerFragment : Fragment() {

    private lateinit var binding: FragmentDayPickerBinding
    private lateinit var viewModel: ExportToInstagramViewModel

    private val calendar = Calendar.getInstance()

    private var dateInPicker = PickedDateData(
        year = calendar.get(Calendar.YEAR),
        month = calendar.get(Calendar.MONTH),
        day = calendar.get(Calendar.DAY_OF_MONTH)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayPickerBinding.inflate(inflater, container, false)

        binding.root.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMoth ->
                    viewModel.dateInDayPickerChanged(
                        PickedDateData(
                            year = year,
                            month = month,
                            day = dayOfMoth
                        )
                    )
                },
                dateInPicker.year,
                dateInPicker.month,
                dateInPicker.day
            ).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)

        observeDateInPickerChanged()
    }

    private fun observeDateInPickerChanged() {
        viewModel.dayInPickerChanged.observe(viewLifecycleOwner) { date ->
            dateInPicker = date
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DayPickerFragment()
    }
}
package gamefield.yourdays.presentation.screen.export_screen

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gamefield.yourdays.databinding.FragmentDayPickerBinding
import gamefield.yourdays.presentation.screen.export_screen.view_model.PickedDateData
import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DayPickerFragment : Fragment() {

    private lateinit var binding: FragmentDayPickerBinding
    private val viewModel: ExportToInstagramViewModel by viewModel()
    private val calendar: Calendar by inject()

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

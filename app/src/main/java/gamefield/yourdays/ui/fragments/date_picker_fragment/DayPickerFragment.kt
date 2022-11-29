package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gamefield.yourdays.databinding.FragmentDayPickerBinding

class DayPickerFragment : Fragment() {


    private lateinit var binding: FragmentDayPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayPickerBinding.inflate(inflater, container, false)

        binding.root.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMoth ->

                },
                2022,
                11,
                29
            ).show()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DayPickerFragment()
    }
}
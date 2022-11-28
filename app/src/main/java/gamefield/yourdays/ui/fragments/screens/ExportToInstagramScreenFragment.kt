package gamefield.yourdays.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.Navigation
import gamefield.yourdays.databinding.FragmentExportToInstagramScreenBinding
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class ExportToInstagramScreenFragment : Fragment() {

    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentExportToInstagramScreenBinding
    private lateinit var viewModel: ExportToInstagramViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        navigation = requireActivity() as Navigation
        binding.closeButton.setOnClickListener {
            navigation.goBack()
        }
        binding.monthButton.setOnClickListener {
            viewModel.onMonthButtonClicked()
        }
        binding.dayButton.setOnClickListener {
            viewModel.onDayButtonClicked()
        }
        binding.periodButton.setOnClickListener {
            viewModel.onDateButtonClicked()
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
        viewModel.openEnterDayDialog.observe(viewLifecycleOwner) {

        }
        viewModel.openEnterMonthDialog.observe(viewLifecycleOwner) {
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(monthNumber: Int? = null, dayNumber: Int? = null) =
            ExportToInstagramScreenFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
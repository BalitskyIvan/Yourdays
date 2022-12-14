package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentDayPreviewFragmentBinding
import gamefield.yourdays.ui.customviews.emotions.EmptyEmotionView
import gamefield.yourdays.ui.customviews.emotions.MinusEmotionView
import gamefield.yourdays.ui.customviews.emotions.PlusEmotionView
import gamefield.yourdays.ui.customviews.emotions.ZeroEmotionView
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class DayPreviewFragment : Fragment() {

    private lateinit var viewModel: ExportToInstagramViewModel
    private lateinit var binding: FragmentDayPreviewFragmentBinding

    private lateinit var emptyEmotionView: EmptyEmotionView
    private lateinit var plusEmotionView: PlusEmotionView
    private lateinit var minusEmotionView: MinusEmotionView
    private lateinit var zeroEmotionView: ZeroEmotionView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayPreviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)

        emptyEmotionView = EmptyEmotionView(context = requireContext())
        plusEmotionView = PlusEmotionView(context = requireContext())
        minusEmotionView = MinusEmotionView(context = requireContext())
        zeroEmotionView = ZeroEmotionView(context = requireContext())

        binding.dayPreviewEmotionContainer.addView(emptyEmotionView)
    }

    private fun observeDayChangedEvent() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = DayPreviewFragment()
    }
}
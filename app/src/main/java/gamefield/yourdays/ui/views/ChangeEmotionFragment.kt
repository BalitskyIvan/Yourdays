package gamefield.yourdays.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.databinding.FragmentChangeEmotionBinding
import gamefield.yourdays.utils.EmotionSeekBarListener
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class ChangeEmotionFragment : Fragment() {

    private lateinit var binding: FragmentChangeEmotionBinding
    private lateinit var viewModel: MainScreenFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())
            .get(MainScreenFragmentViewModel::class.java)

        binding = FragmentChangeEmotionBinding.inflate(inflater, container, false)

        setSeekBarListeners()

        return binding.root
    }

    private fun setSeekBarListeners() {
        with(binding) {
            anxiety.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::anxietyChanged))
            joy.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::joyChanged))
            sadness.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::sadnessChanged))
            calmness.setOnSeekBarChangeListener(EmotionSeekBarListener(viewModel::calmnessChanged))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeEmotionFragment()
    }
}
package gamefield.yourdays.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentMainScreenBinding
import gamefield.yourdays.ui.views.ChangeEmotionFragment
import gamefield.yourdays.ui.views.MainScreenEmotionFragment
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var viewModel: MainScreenFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.change_emotion_container, ChangeEmotionFragment.newInstance())
            .commitNow()

        childFragmentManager
            .beginTransaction()
            .replace(R.id.emotion_container, MainScreenEmotionFragment.newInstance())
            .commitNow()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())
            .get(MainScreenFragmentViewModel::class.java)
        binding.scrollingPeriod.root.setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
            viewModel.onEmotionPeriodScrolled(y = p2)
        }
        observeEmotionActions()
    }

    private fun observeEmotionActions() {
        viewModel.clickEmotionFillEvent.observe(viewLifecycleOwner) {
            binding.scrollingPeriod.root.smoothScrollTo(0, 0)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainScreenFragment()
    }
}
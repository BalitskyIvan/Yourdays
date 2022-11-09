package gamefield.yourdays.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentMainScreenBinding
import gamefield.yourdays.ui.adapter.MonthAdapter
import gamefield.yourdays.ui.fragments.emotion_fragment.ChangeEmotionFragment
import gamefield.yourdays.ui.fragments.emotion_fragment.MainScreenEmotionFragment
import gamefield.yourdays.viewmodels.MainScreenFragmentViewModel

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var viewModel: MainScreenFragmentViewModel
    private val monthAdapter = MonthAdapter()

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
        viewModel.initDatabaseWithContext(requireActivity().applicationContext)
        binding.mainScreeScrollView.setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
            viewModel.onEmotionPeriodScrolled(y = p2)
        }
        with(binding.monthRecycler) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = monthAdapter
        }
        observeEmotionActions()
        observeMoths()
    }

    private fun observeEmotionActions() {
        viewModel.clickEmotionFillEvent.observe(viewLifecycleOwner) {
            binding.mainScreeScrollView.smoothScrollTo(0, 0)
        }
    }

    private fun observeMoths() {
        viewModel.mothListChangedEvent.observe(viewLifecycleOwner) {
            monthAdapter.months.clear()
            monthAdapter.months.addAll(it)
            monthAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }
}
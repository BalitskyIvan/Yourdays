package gamefield.yourdays.presentation.screen.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentMainScreenBinding
import gamefield.yourdays.presentation.components.adapter.MonthAdapter
import gamefield.yourdays.domain.analytics.AnalyticsTracks
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenFragmentViewModel

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var viewModel: MainScreenFragmentViewModel
    private lateinit var monthAdapter: MonthAdapter

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
        viewModel.initWithContext(
            context = requireActivity().applicationContext,
            analyticsTracks = requireActivity() as AnalyticsTracks
        )
        binding.mainScreeScrollView.setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
            viewModel.onEmotionPeriodScrolled(y = p2)
        }
        monthAdapter = MonthAdapter(onDayClickedAction = viewModel::onDaySelected)
        with(binding.monthRecycler) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = monthAdapter
        }
        observeEmotionActions()
        observeMoths()
        observeShowToast()
    }

    private fun observeEmotionActions() {
        viewModel.scrollPeriodToTop.observe(viewLifecycleOwner) {
            binding.mainScreeScrollView.scrollTo(0, 0)
        }
        viewModel.smoothScrollPeriodToTop.observe(viewLifecycleOwner) {
            binding.mainScreeScrollView.smoothScrollTo(0, 0)
        }
    }

    private fun observeShowToast() {
        viewModel.showCantChangeEmotionToastEvent.observe(viewLifecycleOwner) { show ->

            if (show != null && show)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.cant_change_error_toast),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun observeMoths() {
        viewModel.firstDayOfWeekChangedEvent.observe(viewLifecycleOwner) { firstDayOfWeek ->
            monthAdapter.firstDayOfWeek = firstDayOfWeek
            monthAdapter.notifyDataSetChanged()
        }
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
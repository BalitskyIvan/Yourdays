package gamefield.yourdays.presentation.screen.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import gamefield.yourdays.R
import gamefield.yourdays.databinding.FragmentMainScreenBinding
import gamefield.yourdays.presentation.components.adapter.MonthAdapter
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenViewModel
import org.koin.androidx.fragment.android.replace
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel: MainScreenViewModel by activityViewModel()
    private lateinit var monthAdapter: MonthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        childFragmentManager
            .beginTransaction()
            .replace<ChangeEmotionFragment>(R.id.change_emotion_container)
            .commitNow()
        childFragmentManager
            .beginTransaction()
            .replace<MainScreenEmotionFragment>(R.id.emotion_container)
            .commitNow()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}

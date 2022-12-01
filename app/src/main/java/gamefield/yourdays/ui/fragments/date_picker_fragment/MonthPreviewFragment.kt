package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.databinding.FragmentMonthPreviewFragmetBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.getEmotionViewFromDay
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.ui.adapter.DayContainer
import gamefield.yourdays.utils.export_screen.MonthPreviewUtils
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class MonthPreviewFragment : Fragment() {

    private lateinit var binding: FragmentMonthPreviewFragmetBinding
    private lateinit var viewModel: ExportToInstagramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthPreviewFragmetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)

        observeMonthChanged()
    }

    private fun observeMonthChanged() {
        viewModel.currentMonthChanged.observe(viewLifecycleOwner) { month ->
            binding.cardPeriodMonthName.text =
                month.monthNumber.getMonthName(context = requireContext(), year = month.year)

            if (month.weeks.isEmpty()) {
                binding.root.visibility = View.GONE
            } else {
                binding.root.visibility = View.VISIBLE
                initDays(month)
            }
        }
    }

    private fun initDays(month: Month) {
        with(month) {
            weeks[0].days[0].getEmotionViewFromDay(requireContext()).let { binding.firstWeekMon.setEmotion(it) }
            weeks[0].days[1].getEmotionViewFromDay(requireContext()).let { binding.firstWeekTue.setEmotion(it) }
            weeks[0].days[2].getEmotionViewFromDay(requireContext()).let { binding.firstWeekWed.setEmotion(it) }
            weeks[0].days[3].getEmotionViewFromDay(requireContext()).let { binding.firstWeekThu.setEmotion(it) }
            weeks[0].days[4].getEmotionViewFromDay(requireContext()).let { binding.firstWeekFri.setEmotion(it) }
            weeks[0].days[5].getEmotionViewFromDay(requireContext()).let { binding.firstWeekSat.setEmotion(it) }
            weeks[0].days[6].getEmotionViewFromDay(requireContext()).let { binding.firstWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterFirstWeekVisibility(binding, weeks))
                return

            weeks[1].days[0].getEmotionViewFromDay(requireContext()).let { binding.secondWeekMon.setEmotion(it) }
            weeks[1].days[1].getEmotionViewFromDay(requireContext()).let { binding.secondWeekTue.setEmotion(it) }
            weeks[1].days[2].getEmotionViewFromDay(requireContext()).let { binding.secondWeekWed.setEmotion(it) }
            weeks[1].days[3].getEmotionViewFromDay(requireContext()).let { binding.secondWeekThu.setEmotion(it) }
            weeks[1].days[4].getEmotionViewFromDay(requireContext()).let { binding.secondWeekFri.setEmotion(it) }
            weeks[1].days[5].getEmotionViewFromDay(requireContext()).let { binding.secondWeekSat.setEmotion(it) }
            weeks[1].days[6].getEmotionViewFromDay(requireContext()).let { binding.secondWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterSecondWeekVisibility(binding, weeks))
                return

            weeks[2].days[0].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekMon.setEmotion(it) }
            weeks[2].days[1].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekTue.setEmotion(it) }
            weeks[2].days[2].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekWed.setEmotion(it) }
            weeks[2].days[3].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekThu.setEmotion(it) }
            weeks[2].days[4].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekFri.setEmotion(it) }
            weeks[2].days[5].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekSat.setEmotion(it) }
            weeks[2].days[6].getEmotionViewFromDay(requireContext()).let { binding.thirdWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterThirdWeekVisibility(binding, weeks))
                return

            weeks[3].days[0].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekMon.setEmotion(it) }
            weeks[3].days[1].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekTue.setEmotion(it) }
            weeks[3].days[2].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekWed.setEmotion(it) }
            weeks[3].days[3].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekThu.setEmotion(it) }
            weeks[3].days[4].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekFri.setEmotion(it) }
            weeks[3].days[5].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekSat.setEmotion(it) }
            weeks[3].days[6].getEmotionViewFromDay(requireContext()).let { binding.fourthWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterFourthWeekVisibility(binding, weeks))
                return

            weeks[4].days[0].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekMon.setEmotion(it) }
            weeks[4].days[1].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekTue.setEmotion(it) }
            weeks[4].days[2].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekWed.setEmotion(it) }
            weeks[4].days[3].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekThu.setEmotion(it) }
            weeks[4].days[4].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekFri.setEmotion(it) }
            weeks[4].days[5].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekSat.setEmotion(it) }
            weeks[4].days[6].getEmotionViewFromDay(requireContext()).let { binding.fifthWeekSun.setEmotion(it) }

        }
    }

    private fun FrameLayout.setEmotion(dayContainer: DayContainer?) {
        removeAllViews()

        if (dayContainer != null && dayContainer.emotion.type != EmotionType.NONE)
            addView(dayContainer.view)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MonthPreviewFragment()
    }
}

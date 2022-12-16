package gamefield.yourdays.ui.fragments.date_picker_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.databinding.FragmentMonthPreviewFragmetBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.domain.usecase.ui.GetMonthMarkupByFirstDayOfWeekUseCase
import gamefield.yourdays.extensions.getEmotionViewFromDay
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.ui.adapter.DayContainer
import gamefield.yourdays.utils.export_screen.MonthPreviewUtils
import gamefield.yourdays.viewmodels.ExportToInstagramViewModel

class MonthPreviewFragment : Fragment() {

    private lateinit var binding: FragmentMonthPreviewFragmetBinding
    private lateinit var viewModel: ExportToInstagramViewModel

    private lateinit var getMonthMarkupByFirstDayOfWeekUseCase: GetMonthMarkupByFirstDayOfWeekUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthPreviewFragmetBinding.inflate(inflater, container, false)

        with(binding) {
            getMonthMarkupByFirstDayOfWeekUseCase = GetMonthMarkupByFirstDayOfWeekUseCase(
                markupFirstDay = markupDayMon,
                markupSecondDay = markupDayTue,
                markupThirdDay = markupDayWed,
                markupFourthDay = markupDayThu,
                markupFifthDay = markupDayFri,
                markupSixthDay = markupDaySat,
                markupSeventhDay = markupDaySun
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExportToInstagramViewModel::class.java)

        observeMonthChanged()
    }

    private fun observeMonthChanged() {
        viewModel.currentMonthInPreviewChanged.observe(viewLifecycleOwner) { data ->
            binding.cardPeriodMonthName.text =
                data.first.monthNumber.getMonthName(context = requireContext(), year = data.first.year)

            if (data.first.weeks.isEmpty()) {
                binding.root.visibility = View.GONE
            } else {
                binding.root.visibility = View.VISIBLE
                initDays(month = data.first, firstDayOfWeek = data.second)
            }
        }
    }

    private fun initDays(month: Month, firstDayOfWeek: Int) {

        val daysInWeek = getMonthMarkupByFirstDayOfWeekUseCase.invoke(context = requireContext(), firstDayOfWeek = firstDayOfWeek)

        with(month) {
            weeks[0].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekMon.setEmotion(it) }
            weeks[0].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekTue.setEmotion(it) }
            weeks[0].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekWed.setEmotion(it) }
            weeks[0].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekThu.setEmotion(it) }
            weeks[0].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekFri.setEmotion(it) }
            weeks[0].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekSat.setEmotion(it) }
            weeks[0].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true).let { binding.firstWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterFirstWeekVisibility(binding, weeks))
                return

            weeks[1].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekMon.setEmotion(it) }
            weeks[1].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekTue.setEmotion(it) }
            weeks[1].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekWed.setEmotion(it) }
            weeks[1].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekThu.setEmotion(it) }
            weeks[1].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekFri.setEmotion(it) }
            weeks[1].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekSat.setEmotion(it) }
            weeks[1].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true).let { binding.secondWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterSecondWeekVisibility(binding, weeks))
                return

            weeks[2].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekMon.setEmotion(it) }
            weeks[2].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekTue.setEmotion(it) }
            weeks[2].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekWed.setEmotion(it) }
            weeks[2].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekThu.setEmotion(it) }
            weeks[2].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekFri.setEmotion(it) }
            weeks[2].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekSat.setEmotion(it) }
            weeks[2].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true).let { binding.thirdWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterThirdWeekVisibility(binding, weeks))
                return

            weeks[3].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekMon.setEmotion(it) }
            weeks[3].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekTue.setEmotion(it) }
            weeks[3].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekWed.setEmotion(it) }
            weeks[3].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekThu.setEmotion(it) }
            weeks[3].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekFri.setEmotion(it) }
            weeks[3].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekSat.setEmotion(it) }
            weeks[3].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true).let { binding.fourthWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterFourthWeekVisibility(binding, weeks))
                return

            weeks[4].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekMon.setEmotion(it) }
            weeks[4].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekTue.setEmotion(it) }
            weeks[4].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekWed.setEmotion(it) }
            weeks[4].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekThu.setEmotion(it) }
            weeks[4].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekFri.setEmotion(it) }
            weeks[4].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekSat.setEmotion(it) }
            weeks[4].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true).let { binding.fifthWeekSun.setEmotion(it) }

            if (!MonthPreviewUtils.setAllAfterFifthWeekVisibility(binding, weeks))
                return

            weeks[5].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekMon.setEmotion(it) }
            weeks[5].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekTue.setEmotion(it) }
            weeks[5].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekWed.setEmotion(it) }
            weeks[5].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekThu.setEmotion(it) }
            weeks[5].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekFri.setEmotion(it) }
            weeks[5].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekSat.setEmotion(it) }
            weeks[5].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true).let { binding.sixthWeekSun.setEmotion(it) }
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

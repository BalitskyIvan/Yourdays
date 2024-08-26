package gamefield.yourdays.presentation.screen.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.databinding.FragmentMonthPreviewFragmetBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.domain.usecase.ui.GetMonthMarkupByFirstDayOfWeekUseCase
import gamefield.yourdays.extensions.getEmotionViewFromDay
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.presentation.components.adapter.DayContainer
import gamefield.yourdays.presentation.screen.export_screen.view_model.MonthPreviewUtils
import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MonthPreviewFragment : Fragment() {

    private lateinit var binding: FragmentMonthPreviewFragmetBinding
    private val viewModel: ExportToInstagramViewModel by viewModel()

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
        observeMonthChanged()
    }

    private fun observeMonthChanged() {
        viewModel.currentMonthInPreviewChanged.observe(viewLifecycleOwner) { data ->
            binding.cardPeriodMonthName.text =
                data.first.monthNumber.getMonthName(
                    resources = resources,
                    year = data.first.year
                )

            if (data.first.weeks.isEmpty()) {
                binding.root.visibility = View.GONE
            } else {
                binding.root.visibility = View.VISIBLE
                initDays(month = data.first, firstDayOfWeek = data.second)
            }
        }
    }

    private fun initDays(month: Month, firstDayOfWeek: Int) {

        val daysInWeek = getMonthMarkupByFirstDayOfWeekUseCase.invoke(
            context = requireContext(),
            firstDayOfWeek = firstDayOfWeek
        )

        with(month) {
            weeks[0].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekMon.setEmotion(it, binding.firstWeekMonDate) }
            weeks[0].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekTue.setEmotion(it, binding.firstWeekTueDate) }
            weeks[0].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekWed.setEmotion(it, binding.firstWeekWedDate) }
            weeks[0].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekThu.setEmotion(it, binding.firstWeekThuDate) }
            weeks[0].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekFri.setEmotion(it, binding.firstWeekFriDate) }
            weeks[0].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekSat.setEmotion(it, binding.firstWeekSatDate) }
            weeks[0].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true)
                .let { binding.firstWeekSun.setEmotion(it, binding.firstWeekSunDate) }

            if (!MonthPreviewUtils.setAllAfterFirstWeekVisibility(binding, weeks))
                return

            weeks[1].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekMon.setEmotion(it, binding.secondWeekMonDate) }
            weeks[1].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekTue.setEmotion(it, binding.secondWeekTueDate) }
            weeks[1].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekWed.setEmotion(it, binding.secondWeekWedDate) }
            weeks[1].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekThu.setEmotion(it, binding.secondWeekThuDate) }
            weeks[1].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekFri.setEmotion(it, binding.secondWeekFriDate) }
            weeks[1].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekSat.setEmotion(it, binding.secondWeekSatDate) }
            weeks[1].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true)
                .let { binding.secondWeekSun.setEmotion(it, binding.secondWeekSunDate) }

            if (!MonthPreviewUtils.setAllAfterSecondWeekVisibility(binding, weeks))
                return

            weeks[2].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekMon.setEmotion(it, binding.thirdWeekMonDate) }
            weeks[2].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekTue.setEmotion(it, binding.thirdWeekTueDate) }
            weeks[2].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekWed.setEmotion(it, binding.thirdWeekWedDate) }
            weeks[2].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekThu.setEmotion(it, binding.thirdWeekThuDate) }
            weeks[2].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekFri.setEmotion(it, binding.thirdWeekFriDate) }
            weeks[2].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekSat.setEmotion(it, binding.thirdWeekSatDate) }
            weeks[2].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true)
                .let { binding.thirdWeekSun.setEmotion(it, binding.thirdWeekSunDate) }

            if (!MonthPreviewUtils.setAllAfterThirdWeekVisibility(binding, weeks))
                return

            weeks[3].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekMon.setEmotion(it, binding.fourthWeekMonDate) }
            weeks[3].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekTue.setEmotion(it, binding.fourthWeekTueDate) }
            weeks[3].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekWed.setEmotion(it, binding.fourthWeekWedDate) }
            weeks[3].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekThu.setEmotion(it, binding.fourthWeekThuDate) }
            weeks[3].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekFri.setEmotion(it, binding.fourthWeekFriDate) }
            weeks[3].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekSat.setEmotion(it, binding.fourthWeekSatDate) }
            weeks[3].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fourthWeekSun.setEmotion(it, binding.fourthWeekSunDate) }

            if (!MonthPreviewUtils.setAllAfterFourthWeekVisibility(binding, weeks))
                return

            weeks[4].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekMon.setEmotion(it, binding.fifthWeekMonDate) }
            weeks[4].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekTue.setEmotion(it, binding.fifthWeekTueDate) }
            weeks[4].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekWed.setEmotion(it, binding.fifthWeekWedDate) }
            weeks[4].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekThu.setEmotion(it, binding.fifthWeekThuDate) }
            weeks[4].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekFri.setEmotion(it, binding.fifthWeekFriDate) }
            weeks[4].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekSat.setEmotion(it, binding.fifthWeekSatDate) }
            weeks[4].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true)
                .let { binding.fifthWeekSun.setEmotion(it, binding.fifthWeekSunDate) }

            if (!MonthPreviewUtils.setAllAfterFifthWeekVisibility(binding, weeks))
                return

            weeks[5].days[daysInWeek.first].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekMon.setEmotion(it, binding.sixthWeekMonDate) }
            weeks[5].days[daysInWeek.second].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekTue.setEmotion(it, binding.sixthWeekTueDate) }
            weeks[5].days[daysInWeek.third].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekWed.setEmotion(it, binding.sixthWeekWedDate) }
            weeks[5].days[daysInWeek.fourth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekThu.setEmotion(it, binding.sixthWeekThuDate) }
            weeks[5].days[daysInWeek.fifth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekFri.setEmotion(it, binding.sixthWeekFriDate) }
            weeks[5].days[daysInWeek.sixth].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekSat.setEmotion(it, binding.sixthWeekSatDate) }
            weeks[5].days[daysInWeek.seventh].getEmotionViewFromDay(requireContext(), true)
                .let { binding.sixthWeekSun.setEmotion(it, binding.sixthWeekSunDate) }
        }
    }

    private fun FrameLayout.setEmotion(dayContainer: DayContainer?, dateMarkup: TextView) {
        removeAllViews()

        if (dayContainer != null && dayContainer.emotion.type != EmotionType.NONE) {
            dateMarkup.visibility = View.VISIBLE
            dateMarkup.text = dayContainer.dayNumber.toString()

            addView(dayContainer.view)
        } else {
            dateMarkup.visibility = View.INVISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MonthPreviewFragment()
    }
}

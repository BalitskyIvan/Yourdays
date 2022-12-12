package gamefield.yourdays.domain.usecase.io

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.entity.Week
import gamefield.yourdays.domain.models.EmotionType
import java.util.*
import kotlin.collections.ArrayList

class SeedUseCase(
    context: Context
) {

    private val repository =
        Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    private val random = Random(12313)

    operator fun invoke() {
        repository.clear()
        putYear()

    }

    private fun putYear() {
        for (i in 1..12)
            repository.addMonth(
                Month(
                    id = UUID.randomUUID(),
                    monthNumber = i,
                    year = 2022,
                    weeks = mutableListOf(
                        Week(getWeek()),
                        Week(getWeek()),
                        Week(getWeek()),
                        Week(getWeek()),
                        Week(getWeek())
                    )
                )
            )
    }

    private fun getWeek(): MutableList<Day> {
        val arr = ArrayList<Day>()
        for (i in 1..7)
            arr.add(getRandomDay(i))
        return arr
    }

    private fun getRandomDay(number: Int) = Day(
        number, 0, Emotion(
            random.nextInt(100),
            random.nextInt(100),
            random.nextInt(100),
            random.nextInt(100),
            when (random.nextInt(3)) {
                1 -> EmotionType.MINUS
                2 -> EmotionType.PLUS
                else -> EmotionType.ZERO
            }
        )
    )

}
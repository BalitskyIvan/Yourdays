package gamefield.yourdays.domain.usecase

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Month
import java.util.UUID

class AddDayUseCase(
    context: Context
) {

    private val repository = Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    suspend operator fun invoke() {
        repository.addMonth(Month(
            id = UUID.randomUUID(),
            monthNumber = 1,
            year = 2022,
            weeks = listOf()
        ))
    }

}

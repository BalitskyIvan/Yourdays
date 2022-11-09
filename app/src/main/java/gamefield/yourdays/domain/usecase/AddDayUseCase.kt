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

    }

}

package gamefield.yourdays.domain.usecase

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Month
import kotlinx.coroutines.flow.Flow

class GetAllMonthsListUseCase(
    context: Context
) {

    private val repository = Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    suspend operator fun invoke(): Flow<List<Month>> = repository.getMonths()

}

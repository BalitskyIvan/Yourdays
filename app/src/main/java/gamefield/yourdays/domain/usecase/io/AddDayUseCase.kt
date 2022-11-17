package gamefield.yourdays.domain.usecase.io

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository


class AddDayUseCase(
    context: Context
) {

    private val repository = Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    operator fun invoke() {

    }

}

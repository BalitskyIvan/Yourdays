package gamefield.yourdays.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.domain.usecase.AddDayUseCase
import gamefield.yourdays.domain.usecase.GetAllMonthsListUseCase
import gamefield.yourdays.extensions.toImmutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainScreenFragmentViewModel : ViewModel() {

    private val _anxietyEmotionChangedEvent = MutableLiveData<Int>()
    val anxietyEmotionChangedEvent = _anxietyEmotionChangedEvent.toImmutable()

    private val _joyEmotionChangedEvent = MutableLiveData<Int>()
    val joyEmotionChangedEvent = _joyEmotionChangedEvent.toImmutable()

    private val _sadnessEmotionChangedEvent = MutableLiveData<Int>()
    val sadnessEmotionChangedEvent = _sadnessEmotionChangedEvent.toImmutable()

    private val _calmnessEmotionChangedEvent = MutableLiveData<Int>()
    val calmnessEmotionChangedEvent = _calmnessEmotionChangedEvent.toImmutable()

    private val _clickEmotionFillEvent = MutableLiveData<Boolean>()
    val clickEmotionFillEvent = _clickEmotionFillEvent.toImmutable()

    private val _emotionsPeriodScrolled = MutableLiveData<Int>()
    val emotionsPeriodScrolled = _emotionsPeriodScrolled.toImmutable()

    private lateinit var addDayUseCase: AddDayUseCase
    private lateinit var getAllMonthsListUseCase: GetAllMonthsListUseCase

    fun initDatabaseWithContext(context: Context) {
        addDayUseCase = AddDayUseCase(context)
        getAllMonthsListUseCase = GetAllMonthsListUseCase(context)

        viewModelScope.launch(Dispatchers.IO) {
            addDayUseCase.invoke()
        }
    }

    fun anxietyChanged(progress: Int) {
        _anxietyEmotionChangedEvent.value = progress
    }

    fun joyChanged(progress: Int) {
        _joyEmotionChangedEvent.value = progress
    }

    fun calmnessChanged(progress: Int) {
        _calmnessEmotionChangedEvent.value = progress
    }

    fun sadnessChanged(progress: Int) {
        _sadnessEmotionChangedEvent.value = progress
    }

    fun onFillEmotionClicked() {
        _clickEmotionFillEvent.postValue(true)
    }

    fun onEmotionPeriodScrolled(y: Int) {
        _emotionsPeriodScrolled.postValue(y)
    }
}
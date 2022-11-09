package gamefield.yourdays.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.usecase.AddDayUseCase
import gamefield.yourdays.domain.usecase.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.SeedUseCase
import gamefield.yourdays.extensions.toImmutable
import kotlinx.coroutines.Dispatchers
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

    private val _changeEmotionFragmentOpenCloseAction = MutableLiveData<Boolean>()
    val changeEmotionFragmentOpeCloseAction = _changeEmotionFragmentOpenCloseAction.toImmutable()

    private var isChangeEmotionContainerOpened = false

    private val _emotionsPeriodScrolled = MutableLiveData<Int>()
    val emotionsPeriodScrolled = _emotionsPeriodScrolled.toImmutable()

    private val _mothListChangedEvent = MutableLiveData<List<Month>>()
    val mothListChangedEvent = _mothListChangedEvent.toImmutable()

    private lateinit var addDayUseCase: AddDayUseCase
    private lateinit var getAllMonthsListUseCase: GetAllMonthsListUseCase
    private lateinit var seedUseCase: SeedUseCase

    fun initDatabaseWithContext(context: Context) {
        addDayUseCase = AddDayUseCase(context)
        getAllMonthsListUseCase = GetAllMonthsListUseCase(context)
        seedUseCase = SeedUseCase(context)

        viewModelScope.launch(Dispatchers.IO) {
            seedUseCase.invoke()
            addDayUseCase.invoke()
            getAllMonthsListUseCase.invoke().collect { _mothListChangedEvent.postValue(it) }
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

    fun emotionContainerOkButtonClicked() {
        isChangeEmotionContainerOpened = false
        _changeEmotionFragmentOpenCloseAction.postValue(false)
    }

    fun onFillEmotionClicked() {
        if (!isChangeEmotionContainerOpened) {
            isChangeEmotionContainerOpened = true
            _clickEmotionFillEvent.postValue(true)
            _changeEmotionFragmentOpenCloseAction.postValue(true)
        }
    }

    fun onEmotionPeriodScrolled(y: Int) {
        _emotionsPeriodScrolled.postValue(y)
    }
}
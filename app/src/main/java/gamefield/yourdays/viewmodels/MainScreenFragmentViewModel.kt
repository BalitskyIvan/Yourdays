package gamefield.yourdays.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gamefield.yourdays.extensions.toImmutable

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
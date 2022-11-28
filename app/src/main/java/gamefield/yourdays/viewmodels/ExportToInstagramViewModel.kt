package gamefield.yourdays.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gamefield.yourdays.extensions.toImmutable

class ExportToInstagramViewModel : ViewModel() {

    private val _monthButtonAlphaChanged = MutableLiveData<Float>()
    val monthButtonAlphaChanged = _monthButtonAlphaChanged.toImmutable()

    private val _dayButtonAlphaChanged = MutableLiveData<Float>()
    val dayButtonAlphaChanged = _dayButtonAlphaChanged.toImmutable()

    private val _openEnterMonthDialog = MutableLiveData<Boolean>()
    val openEnterMonthDialog = _openEnterMonthDialog.toImmutable()

    private val _openEnterDayDialog = MutableLiveData<Boolean>()
    val openEnterDayDialog = _openEnterDayDialog.toImmutable()

    private var cardType = CardTypeSelected.MONTH

    init {
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
    }

    fun onDayButtonClicked() {
        cardType = CardTypeSelected.DAY
        _monthButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(SELECTED_ALPHA)
    }

    fun onMonthButtonClicked() {
        cardType = CardTypeSelected.MONTH
        _monthButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
    }

    fun onDateButtonClicked() {
        when (cardType) {
            CardTypeSelected.DAY -> _openEnterDayDialog.postValue(true)
            CardTypeSelected.MONTH -> _openEnterMonthDialog.postValue(true)
        }
    }

    fun onUploadButtonClicked() {

    }

    private companion object {
        const val SELECTED_ALPHA = 1f
        const val UNSELECTED_ALPHA = 0.5f
    }

    private enum class CardTypeSelected {
        MONTH,
        DAY
    }
}
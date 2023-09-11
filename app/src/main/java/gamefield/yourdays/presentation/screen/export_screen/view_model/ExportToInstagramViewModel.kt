package gamefield.yourdays.presentation.screen.export_screen.view_model

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetCurrentEmotionFromMonthListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetDateStrFromDateUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetMonthsInMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetYearsInMonthsListUseCase
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.presentation.components.alert_dialog.ErrorType
import gamefield.yourdays.domain.analytics.AnalyticsTracks
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenBackgroundColorClickedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenClosedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenDayChipClickedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenDaySelectorClickedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenMonthChipClickedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenMonthSelectorChangedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenOpenedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenUploadBackgroundBtnClickedEvent
import gamefield.yourdays.domain.analytics.export_to_instagram_screen.ExportToInstagramScreenUploadBtnClickedEvent
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ExportToInstagramViewModel : ViewModel() {

    private val _monthButtonAlphaChanged = MutableLiveData<Float>()
    val monthButtonAlphaChanged = _monthButtonAlphaChanged.toImmutable()

    private val _dayButtonAlphaChanged = MutableLiveData<Float>()
    val dayButtonAlphaChanged = _dayButtonAlphaChanged.toImmutable()

    private val _periodPickerChanged = MutableLiveData<DatePickerType>()
    val periodPickerChanged = _periodPickerChanged.toImmutable()

    private val _showErrorAlertEvent = MutableLiveData<ErrorType?>()
    val showErrorAlertEvent = _showErrorAlertEvent.toImmutable()

    private val _uploadMonthEvent = MutableLiveData<Boolean?>()
    val uploadMonthEvent = _uploadMonthEvent.toImmutable()

    private val _uploadDayEvent = MutableLiveData<Boolean?>()
    val uploadDayEvent = _uploadDayEvent.toImmutable()

    private val _monthListChangedEvent = MutableLiveData<List<Month>>()

    //Pickers
    private val _monthListInPickerChanged = MutableLiveData<Set<Pair<String, Int>>>()
    val monthListInPickerChanged = _monthListInPickerChanged.toImmutable()

    private val _yearsListInPickerChanged = MutableLiveData<Set<String>>()
    val yearsListInPickerChanged = _yearsListInPickerChanged.toImmutable()

    private val _monthValueInPickerChanged = MutableLiveData<Int>()
    val monthValueInPickerChanged = _monthValueInPickerChanged.toImmutable()

    private val _yearsValueInPickerChanged = MutableLiveData<Int>()
    val yearsValueInPickerChanged = _yearsValueInPickerChanged.toImmutable()

    private val _dayInPickerChanged = MutableLiveData<PickedDateData>()
    val dayInPickerChanged = _dayInPickerChanged.toImmutable()

    private val _currentMonthInPreviewChanged = MutableLiveData<Pair<Month, Int>>()
    val currentMonthInPreviewChanged = _currentMonthInPreviewChanged.toImmutable()

    private val _currentDayInPreviewChanged = MutableLiveData<Pair<String, Emotion?>>()
    val currentDayInPreviewChanged = _currentDayInPreviewChanged.toImmutable()


    private val _openInstagramEvent = MutableLiveData<OpenInstagramData?>()
    val openInstagramEvent = _openInstagramEvent.toImmutable()

    private val _firstDayOfWeekChangedEvent = MutableLiveData(1)

    private val _colorUnselectedEvent = MutableLiveData<InstagramStoriesBackgroundColor>()
    val colorUnselectedEvent = _colorUnselectedEvent.toImmutable()

    private val _colorSelectedEvent = MutableLiveData<InstagramStoriesBackgroundColor>()
    val colorSelectedEvent = _colorSelectedEvent.toImmutable()

    private val _backgroundImagePickedEvent = MutableLiveData<Uri?>()
    val backgroundImagePickedEvent = _backgroundImagePickedEvent.toImmutable()

    private var cardType = DatePickerType.MONTH
    private var calendar = Calendar.getInstance()

    private var initDate: PickedDateData? = null

    private lateinit var getMonthsInMonthsListUseCase: GetMonthsInMonthsListUseCase
    private lateinit var getDateStrFromDateUseCase: GetDateStrFromDateUseCase
    private val getYearsInMonthsListUseCase = GetYearsInMonthsListUseCase()
    private val getCurrentEmotionFromMonthListUseCase = GetCurrentEmotionFromMonthListUseCase()

    private lateinit var analyticsTracks: AnalyticsTracks

    init {
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
        observeMonthListChanged()
    }

    fun initWithContext(context: Context, analyticsTracks: AnalyticsTracks) {
        _showErrorAlertEvent.value = null
        _openInstagramEvent.value = null
        _uploadMonthEvent.value = null
        _uploadDayEvent.value = null

        this.analyticsTracks = analyticsTracks
        analyticsTracks.logEvent(ExportToInstagramScreenOpenedEvent())

        getMonthsInMonthsListUseCase = GetMonthsInMonthsListUseCase(context = context)
        getDateStrFromDateUseCase = GetDateStrFromDateUseCase(context = context)

        if (_monthListInPickerChanged.value == null) {
            val monthNumber = calendar.get(Calendar.MONTH)
            _monthListInPickerChanged.postValue(
                setOf(
                    Pair(
                        monthNumber.getMonthName(isUppercase = false, context = context),
                        monthNumber
                    )
                )
            )
        }
        if (_yearsListInPickerChanged.value == null) {
            _yearsListInPickerChanged.postValue(setOf(calendar.get(Calendar.YEAR).toString()))
        }

        val getAllMonthsListUseCase = GetAllMonthsListUseCase(
            context = context,
            firstDayOfWeekChanged = _firstDayOfWeekChangedEvent,
            mothListChangedEvent = _monthListChangedEvent,
            viewModelScope = viewModelScope,
        )
        getAllMonthsListUseCase.invoke()
    }

    private fun observeMonthListChanged() {
        _monthListChangedEvent.observeForever { monthList ->
            val monthNames = getMonthsInMonthsListUseCase.invoke(monthList)
            val yearNames = getYearsInMonthsListUseCase.invoke(monthList)

            _monthListInPickerChanged.postValue(monthNames)
            _yearsListInPickerChanged.postValue(yearNames)
            setInitDate(monthList)

            val monthInPreview = getMonthInPreview(monthList)
            setCurrentMonthInPicker(
                monthNames = monthNames,
                yearNames = yearNames,
                monthInPreview = monthInPreview
            )

            _currentMonthInPreviewChanged.postValue(
                Pair(monthInPreview, _firstDayOfWeekChangedEvent.value!!)
            )
        }
    }

    private fun setCurrentMonthInPicker(
        monthNames: Set<Pair<String, Int>>,
        yearNames: Set<String>,
        monthInPreview: Month
    ) {
        var currentMonthInPickerIndex = 0
        var currentYearInPickerIndex = 0

        monthNames.forEachIndexed { index, month ->
            if (monthInPreview.monthNumber == month.second)
                currentMonthInPickerIndex = index
        }
        yearNames.forEachIndexed { index, yearStr ->
            if (monthInPreview.year == yearStr.toInt())
                currentYearInPickerIndex = index
        }
        _monthValueInPickerChanged.postValue(currentMonthInPickerIndex)
        _yearsValueInPickerChanged.postValue(currentYearInPickerIndex)
    }

    private fun setInitDate(monthList: List<Month>) {
        initDate?.let { date ->
            _dayInPickerChanged.postValue(date)
            _currentDayInPreviewChanged.postValue(
                Pair(
                    getDateStrFromDateUseCase.invoke(date),
                    getCurrentEmotionFromMonthListUseCase(monthList, date)
                )
            )
        }
    }

    private fun getMonthInPreview(monthList: List<Month>): Month {
        return if (initDate != null) {
            var selectedMonth = monthList.last()
            monthList.forEach { month ->
                if (month.monthNumber == initDate!!.month && month.year == initDate!!.year)
                    selectedMonth = month
            }
            selectedMonth
        } else {
            monthList.last()
        }
    }

    fun initSelectedData(dateData: PickedDateData, isExportDay: Boolean) {
        initDate = dateData
        if (isExportDay)
            onDayButtonClicked()
        else
            onMonthButtonClicked()
    }

    fun dateInDayPickerChanged(newDate: PickedDateData) {
        logDayPicked(dateData = newDate)
        if (_monthListChangedEvent.value != null) {
            _currentDayInPreviewChanged.postValue(
                Pair(
                    getDateStrFromDateUseCase.invoke(dateData = newDate),
                    getCurrentEmotionFromMonthListUseCase.invoke(
                        monthList = _monthListChangedEvent.value!!,
                        date = newDate
                    )
                )
            )
        }
    }

    fun onDayButtonClicked() {
        analyticsTracks.logEvent(ExportToInstagramScreenDayChipClickedEvent())
        cardType = DatePickerType.DAY
        _monthButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.DAY)
    }

    fun onMonthButtonClicked() {
        analyticsTracks.logEvent(ExportToInstagramScreenMonthChipClickedEvent())
        cardType = DatePickerType.MONTH
        _monthButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
    }

    fun onUploadButtonClicked() {
        analyticsTracks.logEvent(ExportToInstagramScreenUploadBtnClickedEvent(previewType = cardType))
        if (cardType == DatePickerType.DAY) {
            if (_currentDayInPreviewChanged.value?.second != null &&
                _currentDayInPreviewChanged.value?.second?.type != EmotionType.NONE
            )
                _uploadDayEvent.postValue(true)
            else
                _showErrorAlertEvent.postValue(ErrorType.DAY_NOT_FILLED_ERROR)
        } else {
            _uploadMonthEvent.postValue(true)
        }
    }

    fun upload(bitmap: Bitmap, context: Context) {
        analyticsTracks.logEvent(ExportToInstagramScreenUploadBackgroundBtnClickedEvent())
        val uri = saveBitmap(
            context = context,
            bitmap = bitmap,
            format = Bitmap.CompressFormat.PNG,
            mimeType = "",
            displayName = Stored_path
        )
        _openInstagramEvent.postValue(
            OpenInstagramData(
                uri = uri,
                background = _backgroundImagePickedEvent.value,
                backgroundColor = _colorSelectedEvent.value
            )
        )
    }

    private fun saveBitmap(
        context: Context, bitmap: Bitmap, format: Bitmap.CompressFormat,
        mimeType: String, displayName: String
    ): Uri {

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }

        var uri: Uri? = null

        return runCatching {
            with(context.contentResolver) {
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
                    uri = it // Keep uri reference so it can be removed on failure

                    openOutputStream(it)?.use { stream ->
                        if (!bitmap.compress(format, 100, stream))
                            throw IOException("Failed to save bitmap.")
                    } ?: throw IOException("Failed to open output stream.")

                } ?: throw IOException("Failed to create new MediaStore record.")
            }
        }.getOrElse {
            _showErrorAlertEvent.postValue(ErrorType.FILE_SYSTEM_ERROR)
            uri?.let { orphanUri ->
                // Don't leave an orphan entry in the MediaStore
                context.contentResolver.delete(orphanUri, null, null)
            }

            throw it
        }
    }

    fun colorSelected(color: InstagramStoriesBackgroundColor) {
        analyticsTracks.logEvent(ExportToInstagramScreenBackgroundColorClickedEvent(color = color))
        _colorSelectedEvent.value?.let { currentColor ->
            _colorUnselectedEvent.postValue(
                currentColor
            )
        }
        _colorSelectedEvent.postValue(color)
    }

    fun onBackgroundImagePicked(uri: Uri?) {
        if (uri != null) {
            _backgroundImagePickedEvent.postValue(uri)
        }
    }

    fun onMonthPickerChanged(monthName: String, yearName: String, context: Context) {
        logMonthPreviewPickerChanged(monthName, yearName)

        if (!changeYearPickerIfNeeded(monthName, yearName)) {
            changeMonthInPreview(monthName, yearName, context)
        }
    }

    fun onYearPickerChanged(monthName: String, yearName: String, context: Context) {
        logMonthPreviewPickerChanged(monthName, yearName)

        if (!changeMonthPickerIfNeeded(monthName, yearName)) {
            changeMonthInPreview(monthName, yearName, context)
        }
    }

    private fun logMonthPreviewPickerChanged(monthName: String, yearName: String) {
        analyticsTracks.logEvent(
            ExportToInstagramScreenMonthSelectorChangedEvent(
                pickedMonth = monthName,
                pickedYear = yearName
            )
        )
    }

    private fun logDayPicked(dateData: PickedDateData) {
        analyticsTracks.logEvent(
            ExportToInstagramScreenDaySelectorClickedEvent(
                pickedDay = "${dateData.day}.${dateData.month}.${dateData.year}"
            )
        )
    }

    private fun changeMonthPickerIfNeeded(monthName: String, yearName: String): Boolean {
        _monthListInPickerChanged.value?.filter { it.first == monthName }?.forEach { month ->
            _monthListChangedEvent.value?.forEach {
                if (month.second == it.monthNumber && it.year == yearName.toInt())
                    return false
            }
        }
        _monthListInPickerChanged.value?.forEachIndexed { monthIndex, month ->
            _monthListChangedEvent.value?.forEach {
                if (month.second == it.monthNumber && it.year == yearName.toInt()) {
                    _monthValueInPickerChanged.postValue(monthIndex)
                    return true
                }
            }
        }
        return false
    }

    private fun changeYearPickerIfNeeded(monthName: String, yearName: String): Boolean {
        _monthListInPickerChanged.value?.forEach { month ->
            if (month.first == monthName) {
                _monthListChangedEvent.value?.forEach {
                    if (it.monthNumber == month.second) {
                        if (it.year == yearName.toInt())
                            return false
                    }
                }
                _monthListChangedEvent.value?.forEach {
                    if (it.monthNumber == month.second) {
                        if (it.year != yearName.toInt()) {
                            _yearsListInPickerChanged.value?.forEachIndexed { index, yearStr ->
                                if (it.year == yearStr.toInt()) {
                                    _yearsValueInPickerChanged.postValue(index)
                                    return true
                                }
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    private fun changeMonthInPreview(monthName: String, yearName: String, context: Context) {
        _monthListChangedEvent.value?.find { month ->
            yearName.toInt() == month.year && month.monthNumber.getMonthName(
                isUppercase = false,
                context = context
            ) == monthName
        }?.let {
            _currentMonthInPreviewChanged.postValue(
                Pair(
                    it,
                    _firstDayOfWeekChangedEvent.value!!
                )
            )
        }
    }

    fun onInstagramError() {
        _showErrorAlertEvent.postValue(ErrorType.INSTAGRAM_NOT_INSTALLED_ERROR)
    }

    override fun onCleared() {
        super.onCleared()
        analyticsTracks.logEvent(ExportToInstagramScreenClosedEvent())
    }

    private companion object {
        const val SELECTED_ALPHA = 1f
        const val UNSELECTED_ALPHA = 0.5f

        val DIRECTORY = Environment.getDownloadCacheDirectory().path + "/YourDays/"
        val FILENAME: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val Stored_path = "$DIRECTORY$FILENAME.png"
    }
}

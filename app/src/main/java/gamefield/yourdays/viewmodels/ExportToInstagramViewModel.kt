package gamefield.yourdays.viewmodels

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
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetCurrentEmotionFromMonthListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetDateStrFromDateUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetMonthsInMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetYearsInMonthsListUseCase
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.utils.emum.DatePickerType
import gamefield.yourdays.utils.export_screen.InstagramStoriesBackgroundColor
import gamefield.yourdays.utils.export_screen.PickedDateData
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

    private val _mothListChangedEvent = MutableLiveData<List<Month>>()


    //Pickers
    private val _monthListInPickerChanged = MutableLiveData<Set<String>>()
    val monthListInPickerChanged = _monthListInPickerChanged.toImmutable()

    private val _yearsListInPickerChanged = MutableLiveData<Set<String>>()
    val yearsListInPickerChanged = _yearsListInPickerChanged.toImmutable()

    private val _dayInPickerChanged = MutableLiveData<PickedDateData>()
    val dayInPickerChanged = _dayInPickerChanged.toImmutable()

    private val _currentMonthInPreviewChanged = MutableLiveData<Pair<Month, Int>>()
    val currentMonthInPreviewChanged = _currentMonthInPreviewChanged.toImmutable()

    private val _currentDayInPreviewChanged = MutableLiveData<Pair<String, Emotion?>>()
    val currentDayInPreviewChanged = _currentDayInPreviewChanged.toImmutable()


    private val _openInstagramEvent = MutableLiveData<Uri>()
    val openInstagramEvent = _openInstagramEvent.toImmutable()

    private val _firstDayOfWeekChangedEvent = MutableLiveData(1)

    private val _colorUnselectedEvent = MutableLiveData<InstagramStoriesBackgroundColor>()
    val colorUnselectedEvent = _colorUnselectedEvent.toImmutable()

    private val _colorSelectedEvent = MutableLiveData<InstagramStoriesBackgroundColor>()
    val colorSelectedEvent = _colorSelectedEvent.toImmutable()

    private val _backgroundImagePickedEvent = MutableLiveData<Uri>()
    val backgroundImagePickedEvent = _backgroundImagePickedEvent.toImmutable()

    private var cardType = DatePickerType.MONTH
    private var calendar = Calendar.getInstance()

    private var initDate: PickedDateData? = null

    private lateinit var getMonthsInMonthsListUseCase: GetMonthsInMonthsListUseCase
    private lateinit var getDateStrFromDateUseCase: GetDateStrFromDateUseCase
    private val getYearsInMonthsListUseCase = GetYearsInMonthsListUseCase()
    private val getCurrentEmotionFromMonthListUseCase = GetCurrentEmotionFromMonthListUseCase()

    init {
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
        observeMonthListChanged()
    }

    fun initWithContext(context: Context) {
        getMonthsInMonthsListUseCase = GetMonthsInMonthsListUseCase(context = context)
        getDateStrFromDateUseCase = GetDateStrFromDateUseCase(context = context)

        _monthListInPickerChanged.postValue(setOf(calendar.get(Calendar.MONTH).getMonthName(isUppercase = false, context = context)))
        _yearsListInPickerChanged.postValue(setOf(calendar.get(Calendar.YEAR).toString()))

        val getAllMonthsListUseCase = GetAllMonthsListUseCase(
            context = context,
            firstDayOfWeekChanged = _firstDayOfWeekChangedEvent,
            mothListChangedEvent = _mothListChangedEvent,
            viewModelScope = viewModelScope,
        )
        getAllMonthsListUseCase.invoke()
    }

    private fun observeMonthListChanged() {
        _mothListChangedEvent.observeForever { monthList ->
            val monthNames = getMonthsInMonthsListUseCase.invoke(monthList)
            val yearNames = getYearsInMonthsListUseCase.invoke(monthList)

            _monthListInPickerChanged.postValue(monthNames)
            _yearsListInPickerChanged.postValue(yearNames)
            initDate?.let { date ->
                _dayInPickerChanged.postValue(date)
                _currentDayInPreviewChanged.postValue(Pair(getDateStrFromDateUseCase.invoke(date), getCurrentEmotionFromMonthListUseCase(monthList, date)))
            }

        //    _currentMonthChanged.postValue(Pair(monthList.last(), _firstDayOfWeekChangedEvent.value!!))

        }
    }

    fun initSelectedData(dateData: PickedDateData) {
        initDate = dateData
    }

    fun onDayButtonClicked() {
        cardType = DatePickerType.DAY
        _monthButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.DAY)
    }

    fun onMonthButtonClicked() {
        cardType = DatePickerType.MONTH
        _monthButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
    }

    fun onUploadButtonClicked(bitmap: Bitmap, context: Context) {
        val uri = saveBitmap(context = context, bitmap = bitmap, format = Bitmap.CompressFormat.PNG, mimeType = "", displayName =  Stored_path)
        _openInstagramEvent.postValue(uri)
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
            uri?.let { orphanUri ->
                // Don't leave an orphan entry in the MediaStore
                context.contentResolver.delete(orphanUri, null, null)
            }

            throw it
        }
    }

    fun colorSelected(color: InstagramStoriesBackgroundColor) {
        _colorSelectedEvent.value?.let { currentColor -> _colorUnselectedEvent.postValue(currentColor) }
        _colorSelectedEvent.postValue(color)
    }

    fun onBackgroundImagePicked(uri: Uri?) {
        if (uri != null) {
            _backgroundImagePickedEvent.postValue(uri)
        }
    }

    fun onMonthPickerChanged(monthName: String, yearName: String, context: Context) {
        val pickedMonth = _mothListChangedEvent.value?.find { month ->
            yearName.toInt() == month.year && month.monthNumber.getMonthName(isUppercase = false, context = context) == monthName
        }
        pickedMonth?.let { _currentMonthInPreviewChanged.postValue(Pair(it, _firstDayOfWeekChangedEvent.value!!)) }
    }

    private companion object {
        const val SELECTED_ALPHA = 1f
        const val UNSELECTED_ALPHA = 0.5f

        val DIRECTORY = Environment.getDownloadCacheDirectory().path + "/YourDays/"
        val FILENAME = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val Stored_path = "$DIRECTORY$FILENAME.png"
    }
}
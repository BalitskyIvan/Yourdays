package gamefield.yourdays.viewmodels

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetMonthsInMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetYearsInMonthsListUseCase
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.utils.emum.DatePickerType
import java.io.File
import java.io.FileFilter
import java.io.FileOutputStream
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
    val mothListChangedEvent = _mothListChangedEvent.toImmutable()

    private val _monthListInPickerChanged = MutableLiveData<Set<String>>()
    val monthListInPickerChanged = _monthListInPickerChanged.toImmutable()

    private val _openInstagramEvent = MutableLiveData<Uri>()
    val openInstagramEvent = _openInstagramEvent.toImmutable()

    private val _currentMonthChanged = MutableLiveData<Month>()
    val currentMonthChanged = _currentMonthChanged.toImmutable()

    private val _yearsListInPickerChanged = MutableLiveData<Set<String>>()
    val yearsListInPickerChanged = _yearsListInPickerChanged.toImmutable()

    private var cardType = DatePickerType.MONTH
    private var calendar = Calendar.getInstance()

    private lateinit var getMonthsInMonthsListUseCase: GetMonthsInMonthsListUseCase
    private val getYearsInMonthsListUseCase = GetYearsInMonthsListUseCase()

    init {
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
        observeMonthListChanged()
    }

    fun initWithContext(context: Context) {
        getMonthsInMonthsListUseCase = GetMonthsInMonthsListUseCase(context = context)

        _monthListInPickerChanged.postValue(setOf(calendar.get(Calendar.MONTH).getMonthName(isUppercase = false, context = context)))
        _yearsListInPickerChanged.postValue(setOf(calendar.get(Calendar.YEAR).toString()))

        val getAllMonthsListUseCase = GetAllMonthsListUseCase(
            context = context,
            mothListChangedEvent = _mothListChangedEvent,
            viewModelScope = viewModelScope,
        )
        getAllMonthsListUseCase.invoke()
    }

    private fun observeMonthListChanged() {
        mothListChangedEvent.observeForever { monthList ->
            val monthNames = getMonthsInMonthsListUseCase.invoke(monthList)
            val yearNames = getYearsInMonthsListUseCase.invoke(monthList)

            _monthListInPickerChanged.postValue(monthNames)
            _yearsListInPickerChanged.postValue(yearNames)
        }
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

    fun onMonthPickerChanged(monthName: String, yearName: String, context: Context) {
        val pickedMonth = _mothListChangedEvent.value?.find { month ->
            yearName.toInt() == month.year && month.monthNumber.getMonthName(isUppercase = false, context = context) == monthName
        }
        pickedMonth?.let { _currentMonthChanged.postValue(it) }
    }

    private companion object {
        const val SELECTED_ALPHA = 1f
        const val UNSELECTED_ALPHA = 0.5f

        val DIRECTORY = Environment.getDownloadCacheDirectory().path + "/YourDays/"
        val FILENAME = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val Stored_path = "$DIRECTORY$FILENAME.png"
    }
}
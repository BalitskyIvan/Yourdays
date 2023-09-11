package gamefield.yourdays.presentation.components.alert_dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import gamefield.yourdays.R

class CommonErrorDialog(private val errorType: ErrorType) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fragmentActivity ->
            AlertDialog
                .Builder(fragmentActivity)
                .setMessage(getErrorText())
                .setPositiveButton(R.string.ok_text) { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getErrorText(): String =
        when (errorType) {
            ErrorType.UNEXPECTED_ERROR -> getString(R.string.unexpected_error)
            ErrorType.FILE_SYSTEM_ERROR -> getString(R.string.file_system_error)
            ErrorType.INSTAGRAM_NOT_INSTALLED_ERROR -> getString(R.string.instagram_not_installed_error)
            ErrorType.DAY_NOT_FILLED_ERROR -> getString(R.string.day_not_filled_error)
        }

}

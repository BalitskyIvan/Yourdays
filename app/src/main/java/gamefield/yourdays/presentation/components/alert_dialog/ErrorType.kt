package gamefield.yourdays.presentation.components.alert_dialog

enum class ErrorType(val type: Int?) {
    UNEXPECTED_ERROR(0),
    FILE_SYSTEM_ERROR(1),
    INSTAGRAM_NOT_INSTALLED_ERROR(3),
    DAY_NOT_FILLED_ERROR(4)
}

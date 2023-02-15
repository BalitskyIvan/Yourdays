package gamefield.yourdays.utils.export_screen

import android.app.Activity
import android.content.Intent

object OpenInstagramUtils {

    private const val OPEN_INSTAGRAM_ACTION = "com.instagram.share.ADD_TO_STORY"
    private const val FACEBOOK_APP_ID = "569307288472145"
    private const val FILE_TYPE =  "image/*"

    private const val APP_ID_INTENT_PARAMETER_NAME = "source_application"
    private const val STICKER_INTENT_PARAMETER_NAME = "interactive_asset_uri"
    private const val START_COLOR_INTENT_PARAMETER_NAME = "top_background_color"
    private const val END_COLOR_INTENT_PARAMETER_NAME = "bottom_background_color"

    fun open(openInstagramData: OpenInstagramData, activity: Activity): Boolean {
        try {
            Intent(OPEN_INSTAGRAM_ACTION).apply {
                type = FILE_TYPE

                putExtra(APP_ID_INTENT_PARAMETER_NAME, FACEBOOK_APP_ID)
                putExtra(STICKER_INTENT_PARAMETER_NAME, openInstagramData.uri)

                openInstagramData.backgroundColor?.let { backgroundColor ->
                    putExtra(START_COLOR_INTENT_PARAMETER_NAME, backgroundColor.startColor)
                    putExtra(END_COLOR_INTENT_PARAMETER_NAME, backgroundColor.endColor)
                }
                openInstagramData.background?.let { backgroundUri ->
                    setDataAndType(backgroundUri, FILE_TYPE)
                }

                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                activity.startActivityForResult(this, 0);
            }
        } catch (e: Throwable) {
            return false
        }
        return true
    }
}
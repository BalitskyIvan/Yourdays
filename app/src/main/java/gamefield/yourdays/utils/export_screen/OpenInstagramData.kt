package gamefield.yourdays.utils.export_screen

import android.net.Uri

data class OpenInstagramData(
    val uri: Uri,
    val background: Uri?,
    val backgroundColor: InstagramStoriesBackgroundColor?
)

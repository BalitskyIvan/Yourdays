package gamefield.yourdays.presentation.screen.export_screen.view_model

import android.net.Uri

data class OpenInstagramData(
    val uri: Uri,
    val background: Uri?,
    val backgroundColor: InstagramStoriesBackgroundColor?
)

package gamefield.yourdays.domain.usecase.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SaveAndGetUriUseCase(
    private val context: Context
) {

    suspend operator fun invoke(bitmap: Bitmap): Uri {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, Stored_path)
            put(MediaStore.MediaColumns.MIME_TYPE, "")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }

        var uri: Uri? = null

        return runCatching {
            with(context.contentResolver) {
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
                    uri = it

                    openOutputStream(it)?.use { stream ->
                        if (!bitmap.compress(format, 100, stream))
                            throw IOException("Failed to save bitmap.")
                    } ?: throw IOException("Failed to open output stream.")

                } ?: throw IOException("Failed to create new MediaStore record.")
            }
        }.getOrElse { error ->
            uri?.let { orphanUri ->
                context.contentResolver.delete(orphanUri, null, null)
            }

            throw error
        }
    }

    private companion object {
        val format = Bitmap.CompressFormat.PNG
        val DIRECTORY = Environment.getDownloadCacheDirectory().path + "/YourDays/"
        val FILENAME: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val Stored_path = "$DIRECTORY$FILENAME.png"
    }
}

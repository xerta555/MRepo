package com.sanmer.mrepo.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.sanmer.mrepo.app.Status
import com.sanmer.mrepo.provider.FileProvider
import java.io.File
import java.io.IOException
import java.io.OutputStream

object MediaStoreUtils {
    private lateinit var cr: ContentResolver
    private val fs = FileProvider

    fun init(context: Context) {
        cr = context.contentResolver
    }

    val Uri.displayName: String get() {
        if (scheme == "file") {
            return toFile().name
        }
        require(scheme == "content") { "Uri lacks 'content' scheme: $this" }
        val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
        cr.query(this, projection, null, null, null)?.use { cursor ->
            val displayNameColumn = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                return cursor.getString(displayNameColumn)
            }
        }
        return this.toString()
    }

    fun Uri.copyTo(new: File) {
        cr.openInputStream(this)?.use { input ->
            cr.openOutputStream(new.toUri())?.use { output ->
                input.copyTo(output)
            }
        }
    }

    @Throws(IOException::class)
    fun File.newOutputStream(): OutputStream? = try {
        cr.openOutputStream(toUri())
    } catch (e: Exception) {
        if (Status.Provider.isSucceeded) {
            fs.getFile(this).newOutputStream()
        } else {
            throw IOException(e)
        }
    }
}
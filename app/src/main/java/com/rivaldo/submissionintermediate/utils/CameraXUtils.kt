package com.rivaldo.submissionintermediate.utils

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import com.rivaldo.submissionintermediate.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val outputDirectory = if (mediaDir != null && mediaDir.exists()) mediaDir
    else application.filesDir
    return File(outputDirectory, "$timeStamp.jpg")
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false) : Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}

fun rotateBitmapToFile(bitmap: Bitmap, isBackCamera: Boolean = false) : File {
    val rotatedBitmap = rotateBitmap(bitmap = bitmap, isBackCamera = isBackCamera)
    val file = createFile(application = Application())
    val fileOutputStream = FileOutputStream(file)
    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()
    return file
}

fun compressFile(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var quality = 100
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    while (stream.toByteArray().size / 1024 > 900) {
        quality -= 10
        stream.reset()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    }
    val compressedBitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().size)
    stream.close()

    val compressedFile = File(file.parent, "compressed_" + file.name)
    val fileOutputStream = FileOutputStream(compressedFile)
    compressedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()

    return compressedFile
}




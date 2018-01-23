package com.sentia.android.base.vis.sentialibrary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import io.reactivex.Observable
import java.io.ByteArrayOutputStream

class ImageCompressor(private val context: Context,
                      private val imageQuality: Int = 60,
                      private val imageWidth: Int = 1024,
                      private val imageHeight: Int = 1024) {

    //read-only access mode for contentResolver
    private val fileReadMode = "r"

    fun compressAsBitmap(uri: Uri): Observable<Bitmap?> = Observable.fromCallable {
        decodeSampledBitmapFromFile(uri)
    }

    fun compressAsByteArray(uri: Uri): Observable<ByteArray> = compressAsBitmap(uri).map { bitmap: Bitmap ->
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, bos)
        bos.toByteArray()
    }

    private fun decodeSampledBitmapFromFile(uri: Uri): Bitmap? {
        var image: Bitmap? = null
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, fileReadMode)

        if (parcelFileDescriptor != null) {
            val fileDescriptor = parcelFileDescriptor.fileDescriptor

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, imageWidth, imageHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)

            parcelFileDescriptor.close()
        }

        return image
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}
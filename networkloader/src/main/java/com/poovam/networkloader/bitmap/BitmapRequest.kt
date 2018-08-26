package com.poovam.networkloader.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.NetworkInfo
import com.poovam.networkloader.common.ConnectionParam
import com.poovam.networkloader.common.DownloadCallback
import com.poovam.networkloader.common.DownloadTask
import com.poovam.networkloader.common.Result
import com.poovam.networkloader.common.cache.Cache

/**
 * Created by poovam-5255 on 8/19/2018.
 */

class BitmapRequest(callback: DownloadCallback<Bitmap>, networkInfo: NetworkInfo?, connectionParam: ConnectionParam)
    : DownloadTask<Bitmap>(callback, networkInfo, connectionParam) {

    override fun returnValue(response: ByteArray): Result<Bitmap> {
        return Result(byteArrayToBitmap(response))
    }

    override fun getCache(): Cache<Bitmap>? {
        return BitmapMemoryCache()
    }

    /**
     * @param data
     * @return bitmap (from given ByteArray)
     */
    private fun byteArrayToBitmap(data: ByteArray): Bitmap? {
        return try {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
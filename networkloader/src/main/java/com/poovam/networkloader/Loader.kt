package com.poovam.networkloader

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import com.poovam.networkloader.bitmap.BitmapRequest
import com.poovam.networkloader.common.ConnectionParam
import com.poovam.networkloader.error.ErrorObject
import com.poovam.networkloader.common.DownloadCallback
import com.poovam.networkloader.common.Method
import com.poovam.networkloader.string.StringRequest

/**
 * Created by poovam-5255 on 8/18/2018.
 */
class Loader (private val context: Context){


    fun executeStringRequestAsync(url: String, downloadCallback: DownloadCallback<String>, method: Method = Method.GET, connectionParam: ConnectionParam = ConnectionParam()){
        connectionParam.method = method
        val mDownloadTask = StringRequest(downloadCallback, getNetworkInfo(), connectionParam)
        mDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url)
    }

    fun executeBitmapRequest(url: String, method: Method = Method.GET, connectionParam: ConnectionParam = ConnectionParam()): Bitmap?{
        connectionParam.method = method
        val mDownloadTask = BitmapRequest(object : DownloadCallback<Bitmap> {
            override fun failureFromDownload(error: ErrorObject) {

            }

            override fun onProgressUpdate(progressCode: Int, percentComplete: Int) {

            }

            override fun finishDownloading() {

            }

            override fun successFromDownload(result: Bitmap?) {

            }

        }, getNetworkInfo(), connectionParam)
        val a = mDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url).get()?.mResultValue
        return a
    }

    private fun getNetworkInfo(): NetworkInfo{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }
}
package com.poovam.networkloader

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import com.poovam.networkloader.bitmap.BitmapRequest
import com.poovam.networkloader.common.ConnectionParam
import com.poovam.networkloader.common.DownloadCallback
import com.poovam.networkloader.common.Method
import com.poovam.networkloader.common.Result
import com.poovam.networkloader.string.StringRequest

/**
 * Created by poovam-5255 on 8/18/2018.
 * Loader class which will have implementation of different file format that will be supported
 */
//TODO #Qualify with team. We can use observables here itself, But its important we keep the network library lean
class Loader (private val context: Context){


    fun executeStringRequestAsync(url: String, downloadCallback: DownloadCallback<String>,
                                  method: Method = Method.GET,
                                  connectionParam: ConnectionParam = ConnectionParam()): AsyncTask<String, Int, Result<String>?>{
        connectionParam.method = method
        val mDownloadTask = StringRequest(downloadCallback, getNetworkInfo(), connectionParam)
        return mDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url)
    }


    fun executeBitmapRequestAsync(url: String,
                                  downloadCallback: DownloadCallback<Bitmap>,
                                  method: Method = Method.GET,
                                  connectionParam: ConnectionParam = ConnectionParam()): AsyncTask<String, Int, Result<Bitmap>?>{
        connectionParam.method = method
        val mDownloadTask = BitmapRequest(downloadCallback, getNetworkInfo(), connectionParam)
        return mDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url)
    }

    /**
     * Send the request task running to this method
     * It will cancel the running task
     */
    fun <E> cancelRequest(requestTask: AsyncTask<String, Int, Result<E>?>){
        requestTask.cancel(true)
    }

    private fun getNetworkInfo(): NetworkInfo?{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }
}
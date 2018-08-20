package com.poovam.githubdetails.common.framework.network

import android.content.Context
import android.graphics.Bitmap
import com.poovam.networkloader.Loader
import com.poovam.networkloader.error.ErrorObject
import com.poovam.networkloader.common.DownloadCallback
import com.poovam.networkloader.common.Method


/**
 * Created by poovam-5255 on 8/5/2018.
 */
class ApiConnectionImpl(context: Context) : ApiConnection{

    private var requestQueue = Loader(context)

    override fun doGetRequest(url: String, listener: ApiConnection.ConnectionEventListener<String>) {
        requestQueue.executeStringRequestAsync(url,object : DownloadCallback<String>{
            override fun successFromDownload(result: String?) {
                listener.onSuccess(result ?: "")
            }

            override fun failureFromDownload(error: ErrorObject) {
                listener.onFailure(com.poovam.githubdetails.common.framework.network.ErrorObject(Exception()))
            }

            override fun onProgressUpdate(progressCode: Int, percentComplete: Int) {

            }

            override fun finishDownloading() {

            }

        },Method.GET)
    }

    override fun getImage(url: String): Bitmap? {
        return requestQueue.executeBitmapRequest(url,Method.GET)
    }
}
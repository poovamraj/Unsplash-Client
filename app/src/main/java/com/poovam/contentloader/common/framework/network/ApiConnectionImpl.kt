package com.poovam.githubdetails.common.framework.network

import android.content.Context
import android.graphics.Bitmap
import com.poovam.contentloader.common.framework.network.ApiConnection
import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import com.poovam.networkloader.Loader
import com.poovam.networkloader.common.DownloadCallback
import com.poovam.networkloader.common.Method
import com.poovam.networkloader.error.ErrorObject
import io.reactivex.Observable


/**
 * Created by poovam-5255 on 8/5/2018.
 */
class ApiConnectionImpl(context: Context) : ApiConnection {

    private var requestQueue = Loader(context)

    override fun doGetRequest(url: String, listener: ApiConnection.ConnectionEventListener<String>) {
        requestQueue.executeStringRequestAsync(url,object : DownloadCallback<String>{
            override fun successFromDownload(result: String?) {
                listener.onSuccess(result ?: "")
            }

            override fun failureFromDownload(error: ErrorObject) {
                listener.onFailure(AppError(error.exception))
            }

            override fun onProgressUpdate(percentComplete: Int) {

            }

            override fun finishDownloading() {

            }

        },Method.GET)
    }

    override fun getImage(url: String): Observable<ImageBitmapWithProgress?>?{
        return Observable
                .create{ emitter ->
            requestQueue.executeBitmapRequestAsync(url,
                    object : DownloadCallback<Bitmap>{
                        override fun successFromDownload(result: Bitmap?) {
                            if(result != null)
                                emitter.onNext(ImageBitmapWithProgress(result,100))
                        }

                        override fun failureFromDownload(error: ErrorObject) {
                                emitter.onError(error.exception)
                        }

                        override fun onProgressUpdate(percentComplete: Int) {
                            emitter.onNext(ImageBitmapWithProgress(null,percentComplete))
                        }

                        override fun finishDownloading() {

                        }

                    },Method.GET)
        }
    }
}
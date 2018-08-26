package com.poovam.contentloader.common.framework.network

import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import com.poovam.githubdetails.common.framework.network.AppError
import io.reactivex.Observable

/**
 * Created by poovam-5255 on 8/5/2018.
 */
interface ApiConnection {

    companion object {
        val API_BASE_URL = "https://pastebin.com/raw/wgkJgazE"
    }

    fun doGetRequest(url: String, listener : ConnectionEventListener<String>)

    fun getImage(url: String): Observable<ImageBitmapWithProgress?>?

    interface ConnectionEventListener<E> {

        fun onSuccess(response: E?)

        fun onFailure(error: AppError)
    }
}
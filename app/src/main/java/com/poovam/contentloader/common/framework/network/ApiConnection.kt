package com.poovam.githubdetails.common.framework.network

import android.graphics.Bitmap

/**
 * Created by poovam-5255 on 8/5/2018.
 */
interface ApiConnection {

    companion object {
        val API_BASE_URL = "https://pastebin.com/raw/wgkJgazE"
    }

    fun doGetRequest(url: String, listener : ConnectionEventListener<String>)

    fun getImage(url: String): Bitmap?

    interface ConnectionEventListener<E> {

        fun onSuccess(response: E)

        fun onFailure(error: ErrorObject)
    }
}
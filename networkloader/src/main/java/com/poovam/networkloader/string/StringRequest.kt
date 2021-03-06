package com.poovam.networkloader.string

import android.net.NetworkInfo
import com.poovam.networkloader.common.ConnectionParam
import com.poovam.networkloader.common.DownloadCallback
import com.poovam.networkloader.common.DownloadTask
import com.poovam.networkloader.common.Result
import com.poovam.networkloader.common.cache.Cache

/**
 * Created by poovam-5255 on 8/19/2018.
 */

class StringRequest(callback: DownloadCallback<String>, networkInfo: NetworkInfo?, connectionParam: ConnectionParam)
    : DownloadTask<String>(callback, networkInfo, connectionParam) {

    override fun getCache(): Cache<String>? {
        return null
    }

    override fun returnValue(response: ByteArray): Result<String> {
        return Result(String(response))
    }
}
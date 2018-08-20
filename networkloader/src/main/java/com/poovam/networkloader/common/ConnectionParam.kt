package com.poovam.networkloader.common

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ConnectionParam {

    // Timeout for reading InputStream arbitrarily set to 3000ms.
    var readTimeoutMs = 3000

    // Timeout for connection.connect() arbitrarily set to 3000ms.
    var timeoutMs = 3000

    var canGetFromCache = true

    var addToCache = true

    var method = Method.GET
    set(value) {
        field = value
        rawConnectionMethod = value.asString
    }

    private var rawConnectionMethod = method.asString

}
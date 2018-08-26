package com.poovam.networkloader.common

/**
 * Created by poovam-5255 on 8/26/2018.
 * Wrapper class that serves as a union of a result value and an exception. When the download
 * task has completed, either the result value or exception can be a non-null value.
 * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
 */

class Result<E> {
    constructor(mResultValue: E?){
        this.mResultValue = mResultValue
    }
    constructor(mException: Exception){
        this.mException = mException
    }
    var mResultValue: E? = null
    var mException: Exception? = null
}
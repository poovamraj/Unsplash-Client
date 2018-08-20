package com.poovam.networkloader.common

import com.poovam.networkloader.error.ErrorObject


/**
 * Created by poovam-5255 on 8/18/2018.
 */
interface DownloadCallback<T> {

    interface Progress {
        companion object {
            val ERROR = -1
            val CONNECT_SUCCESS = 0
            val GET_INPUT_STREAM_SUCCESS = 1
            val PROCESS_INPUT_STREAM_IN_PROGRESS = 2
            val PROCESS_INPUT_STREAM_SUCCESS = 3
        }
    }

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     */
    fun successFromDownload(result: T?)

    fun failureFromDownload(error: ErrorObject)
    /**
     * Indicate to callback handler any progress update.
     * @param progressCode must be one of the constants defined in DownloadCallback.Progress.
     * @param percentComplete must be 0-100.
     */
    fun onProgressUpdate(progressCode: Int, percentComplete: Int)

    /**
     * Indicates that the download operation has finished. This method is called even if the
     * download hasn't completed successfully.
     */
    fun finishDownloading()
}
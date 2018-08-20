package com.poovam.networkloader.common

import android.accounts.NetworkErrorException
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import com.poovam.networkloader.common.cache.Cache
import com.poovam.networkloader.error.ErrorObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


/**
 * Created by poovam-5255 on 8/18/2018.
 * The download logic lies here
 */
abstract class DownloadTask<E>(private val callback: DownloadCallback<E>,private val networkInfo: NetworkInfo,private val connectionParam: ConnectionParam) : AsyncTask<String, Int, DownloadTask.Result<E>?>() {

    /**
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

    override fun onPreExecute() {
        super.onPreExecute()
        if (!networkInfo.isConnected || (networkInfo.type != ConnectivityManager.TYPE_WIFI &&
                networkInfo.type != ConnectivityManager.TYPE_MOBILE)) {
            callback.failureFromDownload(ErrorObject(NetworkErrorException()))
            cancel(true)
        }
    }

    override fun doInBackground(vararg urls: String?): Result<E>?{
        if (!isCancelled && urls.isNotEmpty()) {
            val urlString = urls[0]
            return try {
                if(urlString != null){
                    val cached = getCache().get(urlString)
                    if (connectionParam.canGetFromCache && cached != null) {
                        return Result(cached)
                    }

                    val url = URL(urlString)
                    val result = downloadUrl(url)
                    if (result != null) {
                        val response = returnValue(result)
                        val resultValue = response.mResultValue
                        if(connectionParam.addToCache && resultValue!= null){
                            getCache().put(urlString,resultValue)
                        }
                        response
                    } else {
                        throw IOException("No response received.")
                    }
                }else{
                    throw IOException("Provided URL is null")
                }
            } catch(e: Exception) {
                Result(e)
            }
        }
        return null
    }

    override fun onPostExecute(result: Result<E>?) {
        super.onPostExecute(result)
        if (result != null) {
            val exception = result.mException
            if (exception != null) {
                callback.failureFromDownload(ErrorObject(exception))
            } else if (result.mResultValue != null) {
                callback.successFromDownload(result.mResultValue)
            }
            callback.finishDownloading()
        }
    }

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    @Throws(IOException::class)
    private fun downloadUrl(url: URL): ByteArray? {
        var stream: InputStream? = null
        var connection: HttpsURLConnection? = null
        var result: ByteArray? = null
        try {
            connection = url.openConnection() as HttpsURLConnection

            with(connection){
                readTimeout = connectionParam.readTimeoutMs
                connectTimeout = connectionParam.timeoutMs
                requestMethod = connectionParam.method.asString
                connect()
            }

            publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS)
            val responseCode = connection.responseCode
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: " + responseCode)
            }

            stream = connection.inputStream
            publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0)
            if (stream != null) {
                result = readStream(stream)
            }
        } finally {
            if (stream != null) {
                stream.close()
            }
            if (connection != null) {
                connection.disconnect()
            }
        }
        return result
    }


    @Throws(IOException::class, UnsupportedEncodingException::class)
    private fun readStream(stream: InputStream): ByteArray {
        val buffer = ByteArrayOutputStream()

        var nRead = 0
        val data = ByteArray(16384)

        while ({nRead = stream.read(data, 0, data.size); nRead}() != -1) {
            buffer.write(data, 0, nRead)
        }

        buffer.flush()

        return buffer.toByteArray()
    }

    abstract fun returnValue(response: ByteArray): Result<E>

    abstract fun getCache(): Cache<E>
}
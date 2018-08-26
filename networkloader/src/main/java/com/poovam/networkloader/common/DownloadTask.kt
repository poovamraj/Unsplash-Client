package com.poovam.networkloader.common

import android.accounts.NetworkErrorException
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import com.poovam.networkloader.common.cache.Cache
import com.poovam.networkloader.common.cache.GenericCache
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
abstract class DownloadTask<E>(private val callback: DownloadCallback<E>,private val networkInfo: NetworkInfo?,private val connectionParam: ConnectionParam) : AsyncTask<String, Int, Result<E>?>() {

    override fun onPreExecute() {
        super.onPreExecute()
        if (networkInfo == null || !networkInfo.isConnected || (networkInfo.type != ConnectivityManager.TYPE_WIFI &&
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
                    if (connectionParam.canGetFromCache) {
                        val cachedValue = getFromCache(urlString)
                        if(cachedValue != null) return cachedValue
                    }

                    val url = URL(urlString)
                    val result = downloadUrl(url)
                    if (result != null) {
                        val response = returnValue(result)
                        val resultValue = response.mResultValue
                        if(connectionParam.addToCache && resultValue!= null){
                            putIntoCache(urlString,result)
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
            val completeFileSize = connection.contentLength
            stream = connection.inputStream
            publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0)
            if (stream != null) {
                result = readStream(stream,completeFileSize)
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
    private fun readStream(stream: InputStream, contentLength: Int): ByteArray {
        val buffer = ByteArrayOutputStream()

        var nRead = 0
        val data = ByteArray(16384)
        var downloadedFileSize = 0
        while ({nRead = stream.read(data, 0, data.size); nRead}() != -1) {
            downloadedFileSize += nRead
            buffer.write(data, 0, nRead)
            if(contentLength > 0){
                callback.onProgressUpdate(((downloadedFileSize.toDouble() / contentLength.toDouble()) * 100).toInt() )
            }
        }

        buffer.flush()

        return buffer.toByteArray()
    }

    private fun putIntoCache(urlString: String, result: ByteArray){
        val cache = getCache()
        if(cache != null){
            val resultValue = returnValue(result).mResultValue
            if(resultValue != null){
                cache.put(urlString,resultValue)
            }
        }else{
            GenericCache().put(urlString,result)
        }
    }

    private fun getFromCache(urlString: String): Result<E>?{
        val cache = getCache()
        if(cache == null){
            val cached = cache?.get(urlString)
            if(cached!=null){
                return Result(cached)
            }
        }else{
            val cached = GenericCache().get(urlString)
            if(cached != null){
                return returnValue(cached)
            }
        }
        return null
    }

    abstract fun returnValue(response: ByteArray): Result<E>

    /**
    returning null uses generic cache
    if no cache should be used at any cost,
    intercept the ##ConnectionParam and set canGetFromCache = false && addToCache = false.
     */
    abstract fun getCache(): Cache<E>?
}
package com.poovam.networkloader.bitmap

import android.graphics.Bitmap
import android.util.LruCache
import com.poovam.networkloader.common.cache.Cache

/**
 * Created by poovam-5255 on 8/20/2018.
 */
class BitmapMemoryCache : Cache<Bitmap> {

    private val cache: LruCache<String, Bitmap>

    init {
        val maxMemory: Long = Runtime.getRuntime().maxMemory() / 1024
        val cacheSize: Int = (maxMemory / 4).toInt()

        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                return (bitmap?.rowBytes ?: 0) * (bitmap?.height ?: 0) / 1024
            }
        }
    }

    override fun put(url: String, item: Bitmap) {
        cache.put(url, item)
    }

    override fun get(url: String): Bitmap? {
        return cache.get(url)
    }

    override fun clear() {
        cache.evictAll()
    }
}
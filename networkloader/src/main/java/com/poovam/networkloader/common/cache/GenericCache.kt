package com.poovam.networkloader.common.cache

import android.util.LruCache

/**
 * Created by poovam-5255 on 8/26/2018.
 */
class GenericCache(maxCacheSizeInBytes: Long? = null) : Cache<ByteArray> {

    private val cache: LruCache<String, ByteArray>

    init {
        val maxMemory: Long = maxCacheSizeInBytes ?: Runtime.getRuntime().maxMemory() / 1024
        val cacheSize: Int = (maxMemory / 4).toInt()

        cache = object : LruCache<String, ByteArray>(cacheSize) {
            override fun sizeOf(key: String?, item : ByteArray?): Int {
                return item?.size ?: 0 / 1024
            }
        }
    }

    override fun put(url: String, item: ByteArray) {
        cache.put(url, item)
    }

    override fun get(url: String): ByteArray? {
        return cache.get(url)
    }

    override fun clear() {
        cache.evictAll()
    }
}
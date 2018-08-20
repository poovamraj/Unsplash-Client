package com.poovam.networkloader.common.cache

/**
 * Created by poovam-5255 on 8/20/2018.
 */
interface Cache <E> {
    fun put(url: String, item: E)
    fun get(url: String): E?
    fun clear()
}
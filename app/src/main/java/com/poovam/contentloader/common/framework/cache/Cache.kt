package com.poovam.githubdetails.common.framework.cache

/**
 * Created by poovam-5255 on 8/5/2018.
 */
interface Cache<E>{

    fun put(cacheObject: E)

    fun get() : E?
}
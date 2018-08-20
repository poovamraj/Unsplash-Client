package com.poovam.contentloader.common

import android.app.Activity
import android.app.Application
import com.poovam.contentloader.BuildConfig
import com.poovam.githubdetails.common.framework.network.ApiConnection
import com.poovam.githubdetails.common.framework.network.ApiConnectionImpl
import com.poovam.githubdetails.common.utilities.logger.AndroidLogger
import com.poovam.githubdetails.common.utilities.logger.Logger

/**
 * Created by poovam-5255 on 8/5/2018.
 *
 * Application bootstrapper which will contain all the services
 *
 */
class ContentLoaderApplication : Application(){

    lateinit var apiConnection: ApiConnection

    lateinit var logger : Logger

    companion object {
        fun get(activity: Activity): ContentLoaderApplication {
            return activity.application as ContentLoaderApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        apiConnection = ApiConnectionImpl(this)
        logger = AndroidLogger(BuildConfig.DEBUG)
    }

    fun getApiConnectionInstance() : ApiConnection{
        return apiConnection
    }

    fun getLoggerInstance(): Logger{
        return logger
    }
}
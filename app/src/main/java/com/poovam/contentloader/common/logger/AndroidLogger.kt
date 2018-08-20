package com.poovam.githubdetails.common.utilities.logger

import android.util.Log

/**
 * Created by poovam-5255 on 8/5/2018.
 */
class AndroidLogger(private val isDebugMode: Boolean) : Logger{

    override fun d(tag: String, message: String) {
        if(isDebugMode){
            Log.d(tag,message)
        }
    }

}
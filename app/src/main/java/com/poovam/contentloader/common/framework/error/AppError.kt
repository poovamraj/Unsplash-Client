package com.poovam.githubdetails.common.framework.network

import android.accounts.NetworkErrorException
import com.poovam.contentloader.R

/**
 * Created by poovam-5255 on 8/5/2018.
 */
open class AppError(exception: Exception){

    companion object {
        val ON_IMG_DOWNLOAD = "ON_IMG_DOWNLOAD"
        val ON_USER_IMG_DOWNLOAD = "ON_USER_IMG_DOWNLOAD"
    }

    var displayString = R.string.unknown_error

    init {
        displayString = when{
            exception is NetworkErrorException -> R.string.not_connected_to_internet
            exception.message == ON_IMG_DOWNLOAD -> R.string.error_downloading_img
            exception.message == ON_USER_IMG_DOWNLOAD -> R.string.error_downloading_usr_img
            else -> R.string.unknown_error
        }
    }
}
package com.poovam.contentloader.home.model.image

import android.graphics.Bitmap
import com.poovam.githubdetails.common.framework.network.ApiConnection

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ImageRepository(private val apiConnection: ApiConnection){

    fun loadImage(url:String): Bitmap?{
        val a =apiConnection.getImage(url)
        return a
    }

}
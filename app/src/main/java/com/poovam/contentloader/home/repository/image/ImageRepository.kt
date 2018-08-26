package com.poovam.contentloader.home.repository.image

import com.poovam.contentloader.common.framework.network.ApiConnection
import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ImageRepository(private val apiConnection: ApiConnection){

    fun loadImage(url:String): Observable<ImageBitmapWithProgress?>? {
        return apiConnection.getImage(url)?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.newThread())
    }

}
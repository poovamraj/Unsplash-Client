package com.poovam.contentloader.home.view.view_model

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import io.reactivex.Observable

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ImageViewModel : ViewModel(){

    var imageURL: Observable<String>? = null

    var height: Int = 0

    var width: Int = 0

    var imageBitmap: Observable<Bitmap?>? = null

}
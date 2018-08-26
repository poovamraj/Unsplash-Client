package com.poovam.contentloader.home.view.model

import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import io.reactivex.Observable

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ImageViewModel {

    var imageURL: String? = null

    var userName: String = "--"

    var height: Int = 0

    var width: Int = 0

    var likes: Int = 0

    var imageBitmap: Observable<ImageBitmapWithProgress?>? = null

    var userImageBitmap : Observable<ImageBitmapWithProgress?>? = null

}
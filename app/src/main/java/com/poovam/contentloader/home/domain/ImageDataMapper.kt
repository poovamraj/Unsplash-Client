package com.poovam.contentloader.home.domain

import com.poovam.contentloader.home.model.image_data.ImageDataEntity
import com.poovam.githubdetails.common.framework.Mapper

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ImageDataMapper(val list: ArrayList<ImageDataEntity>) : Mapper<ArrayList<ImageData>>{

    override fun getMappedObject(): ArrayList<ImageData> {
        return ArrayList(list.map { element ->
            val model = ImageData(element.urls.full,element.height,element.width)
            model
        })
    }

}
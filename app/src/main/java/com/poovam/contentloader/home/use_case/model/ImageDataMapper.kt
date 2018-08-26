package com.poovam.contentloader.home.use_case.model

import com.poovam.contentloader.home.repository.image_data.model.ImageDataEntity
import com.poovam.githubdetails.common.framework.Mapper

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ImageDataMapper(val list: ArrayList<ImageDataEntity>) : Mapper<ArrayList<ImageData>>{

    override fun getMappedObject(): ArrayList<ImageData> {
        return ArrayList(list.map { element ->
            val model = ImageData(
                    element.urls.regular,
                    element.height,
                    element.width,
                    element.likes,
                    element.user.name,
                    element.user.profileImage.medium
            )
            model
        })
    }

}
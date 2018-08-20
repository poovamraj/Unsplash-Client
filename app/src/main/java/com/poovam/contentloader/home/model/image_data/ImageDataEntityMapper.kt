package com.poovam.githubdetails.userdetails.model.user_repos

import com.google.gson.Gson
import com.poovam.contentloader.home.model.image_data.ImageDataEntity
import com.poovam.githubdetails.common.framework.Mapper



/**
 * Created by poovam-5255 on 8/5/2018.
 *
 * parses the network information to consume in data layer
 */
class ImageDataEntityMapper(private val response: String) : Mapper<ArrayList<ImageDataEntity>> {

    override fun getMappedObject(): ArrayList<ImageDataEntity> {
        val list = ArrayList<ImageDataEntity>()
        list.addAll(Gson().fromJson(response,Array<ImageDataEntity>::class.java))
        return list
    }
}
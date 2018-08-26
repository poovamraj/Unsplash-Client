package com.poovam.contentloader.home.use_case.model

/**
 * Created by poovam-5255 on 8/18/2018.
 */

data class ImageData(
        val imageURL : String,
        val height: Int,
        val width: Int,
        val likes: Int,
        val user: String,
        val userProfileImgURL: String
)
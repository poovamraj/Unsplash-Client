package com.poovam.githubdetails.userdetails.model

import com.poovam.contentloader.home.repository.image_data.model.ImageDataEntity
import com.poovam.contentloader.common.framework.network.ApiConnection
import com.poovam.githubdetails.common.framework.network.AppError
import com.poovam.githubdetails.userdetails.model.user_repos.ImageDataEntityMapper

/**
 * Created by poovam-5255 on 8/5/2018.
 * Image data should be accessed only from here
 */

class ImageDataRepository(private val apiConnection: ApiConnection){

    private val userReposUrl = ApiConnection.API_BASE_URL

    //TODO get
    fun getImageDataRepoAsync(onObjectFetched:(ArrayList<ImageDataEntity>)->Unit, onFailure: (AppError)->Unit) {

        apiConnection.doGetRequest(userReposUrl,object : ApiConnection.ConnectionEventListener<String>{
            override fun onSuccess(response: String?) {
                onObjectFetched.invoke(onNetworkCallSuccess(response))
            }

            override fun onFailure(error: AppError) {
                onFailure.invoke(error)
            }
        })
    }

    private fun onNetworkCallSuccess(response: String?) : ArrayList<ImageDataEntity>{
        if(response == null) return ArrayList()
        return ImageDataEntityMapper(response).getMappedObject()
    }
}
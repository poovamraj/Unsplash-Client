package com.poovam.githubdetails.userdetails.model

import com.poovam.contentloader.home.model.image_data.ImageDataEntity
import com.poovam.githubdetails.common.framework.network.ApiConnection
import com.poovam.githubdetails.common.framework.network.ErrorObject
import com.poovam.githubdetails.userdetails.model.user_repos.ImageDataEntityMapper

/**
 * Created by poovam-5255 on 8/5/2018.
 * Image data should be accessed only from here
 */

class ImageDataRepository(private val apiConnection: ApiConnection){

    private val userReposUrl = ApiConnection.API_BASE_URL

    //private val userCache =  UserRepoCache(userData)

    fun getUserReposAsync(onObjectFetched:((ArrayList<ImageDataEntity>)->Unit)) {

        //TODO check cache and then do api call
        apiConnection.doGetRequest(userReposUrl,object : ApiConnection.ConnectionEventListener<String>{
            override fun onSuccess(response: String) {
                onObjectFetched.invoke(onNetworkCallSuccess(response))
            }

            override fun onFailure(error: ErrorObject) {
                onObjectFetched.invoke(onNetworkCallFailure())
            }

        })
    }

    //TODO refresh cache
    private fun onNetworkCallSuccess(response: String) : ArrayList<ImageDataEntity>{
        return ImageDataEntityMapper(response).getMappedObject()
    }


    private fun onNetworkCallFailure():ArrayList<ImageDataEntity>{
        return ArrayList()
        //return userCache.get()
    }
}
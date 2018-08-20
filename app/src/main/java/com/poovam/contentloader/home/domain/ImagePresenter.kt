package com.poovam.githubdetails.userdetails.domain

import android.graphics.Bitmap
import com.poovam.contentloader.home.domain.ImageData
import com.poovam.contentloader.home.domain.ImageDataMapper
import com.poovam.contentloader.home.model.image.ImageRepository
import com.poovam.contentloader.home.model.image_data.ImageDataEntity
import com.poovam.githubdetails.common.framework.Presenter
import com.poovam.githubdetails.common.framework.network.ApiConnection
import com.poovam.githubdetails.common.framework.network.ErrorObject
import com.poovam.githubdetails.userdetails.model.ImageDataRepository
import io.reactivex.Observable
import java.util.*

/**
 * Created by poovam-5255 on 8/5/2018.
 */


class ImagePresenter(apiConnection: ApiConnection,
                     private val onSuccess: ((ArrayList<ImageData>)-> Unit),
                     private val onError: ((ErrorObject)->Unit)) : Presenter{


    private val imageDataRepository = ImageDataRepository(apiConnection)
    private val imageRepository = ImageRepository(apiConnection)

    override fun onCreate() {
        imageDataRepository.getUserReposAsync(this::onImageDataFetched)
    }


    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDestroy() {

    }

    private fun onImageDataFetched(list: ArrayList<ImageDataEntity>){
        onSuccess.invoke(ImageDataMapper(list).getMappedObject())
    }

    fun loadImage(url: String): Observable<Bitmap?>{
        return Observable.defer {
                return@defer Observable
                        .just(imageRepository.loadImage(url))
        }
    }
}
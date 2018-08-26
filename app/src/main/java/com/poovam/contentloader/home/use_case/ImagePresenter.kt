package com.poovam.githubdetails.userdetails.domain

import com.poovam.contentloader.common.framework.network.ApiConnection
import com.poovam.contentloader.home.repository.image.ImageRepository
import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import com.poovam.contentloader.home.repository.image_data.model.ImageDataEntity
import com.poovam.contentloader.home.use_case.model.ImageData
import com.poovam.contentloader.home.use_case.model.ImageDataMapper
import com.poovam.githubdetails.common.framework.Presenter
import com.poovam.githubdetails.common.framework.network.AppError
import com.poovam.githubdetails.userdetails.model.ImageDataRepository
import io.reactivex.Observable
import java.util.*

/**
 * Created by poovam-5255 on 8/5/2018.
 */


class ImagePresenter(apiConnection: ApiConnection,
                     private val onSuccess: ((ArrayList<ImageData>)-> Unit),
                     private val onError: ((AppError)->Unit)) : Presenter{


    private val imageDataRepository = ImageDataRepository(apiConnection)
    private val imageRepository = ImageRepository(apiConnection)

    override fun onCreate() {
        imageDataRepository.getImageDataRepoAsync(this::onImageDataFetched,this::onImageDataFetchFailure)
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

    private fun onImageDataFetchFailure(errorObject: AppError){
        onError.invoke(errorObject)
    }

    fun loadImage(url: String): Observable<ImageBitmapWithProgress?>?{
        return imageRepository.loadImage(url)?.cache()
    }
}
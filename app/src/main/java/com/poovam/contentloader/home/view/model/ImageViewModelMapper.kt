package com.poovam.contentloader.home.view.model

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import com.poovam.contentloader.home.use_case.model.ImageData
import com.poovam.githubdetails.common.framework.Mapper
import io.reactivex.Observable


/**
 * Created by poovam-5255 on 8/18/2018.
 */
class ImageViewModelMapper(private val activity: AppCompatActivity,
                           private val list: ArrayList<ImageData>,
                           private val fetchImage: (String) -> Observable<ImageBitmapWithProgress?>?)
    : Mapper<ImageViewModelHolder> {


    override fun getMappedObject(): ImageViewModelHolder {
        val imgViewModels = ArrayList<ImageViewModel>()
        for(i in list){

            val model = ImageViewModel()

            with(model){
                imageURL = i.imageURL
                height = i.height
                width = i.width
                likes = i.likes
                userName = i.user
                imageBitmap = fetchImage(i.imageURL)
                userImageBitmap = fetchImage(i.userProfileImgURL)
            }

            imgViewModels.add(model)
        }

        val model = ViewModelProviders.of(activity).get(ImageViewModelHolder::class.java)
        model.viewModel = imgViewModels
        return model
    }

}
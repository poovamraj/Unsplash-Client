package com.poovam.contentloader.home.view.view_model

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.poovam.contentloader.home.domain.ImageData
import com.poovam.githubdetails.common.framework.Mapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by poovam-5255 on 8/18/2018.
 */
class ImageViewModelMapper(private val activity: AppCompatActivity,
                           private val list: ArrayList<ImageData>,
                           private val onImgURLChanged: (String) -> Observable<Bitmap?>)
    : Mapper<ArrayList<ImageViewModel>> {


    companion object {
        var a = 1
        var b = 1
        var c = 1
        var d = 1
    }

    override fun getMappedObject(): ArrayList<ImageViewModel> {
        val imgViewModels = ArrayList<ImageViewModel>()
        for(i in list){
            val model = ViewModelProviders.of(activity).get(ImageViewModel::class.java)
            Log.d("map called","map  "+b++)

            model.imageURL?.subscribe {
                url -> onImgURLChanged.invoke(url)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        bitmap -> model.imageBitmap = Observable.just(bitmap).observeOn(AndroidSchedulers.mainThread())
                        Log.d("bitmaprecived","bit  "+a++)
                    },{
                        Log.d("Error","error"+d++)
                    },{
                        Log.d("oncomplete","oncomplete  "+c++)
                    })
            }

            model.imageURL = Observable.just(
                    i.imageURL
            )

            with(model){
                height = i.height
                width = i.width
            }

            imgViewModels.add(model)
        }
        return imgViewModels
    }

}
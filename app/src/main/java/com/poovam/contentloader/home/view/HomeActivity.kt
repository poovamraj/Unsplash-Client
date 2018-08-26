package com.poovam.contentloader.home.view

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.poovam.contentloader.R
import com.poovam.contentloader.common.ContentLoaderApplication
import com.poovam.contentloader.common.framework.network.ApiConnection
import com.poovam.contentloader.home.repository.image.model.ImageBitmapWithProgress
import com.poovam.contentloader.home.use_case.model.ImageData
import com.poovam.contentloader.home.view.adapter.HomeAdapter
import com.poovam.contentloader.home.view.listener.ImageInteractionListener
import com.poovam.contentloader.home.view.model.ImageViewModel
import com.poovam.contentloader.home.view.model.ImageViewModelMapper
import com.poovam.contentloader.home.view.views.DetailsView
import com.poovam.githubdetails.common.framework.network.AppError
import com.poovam.githubdetails.userdetails.domain.ImagePresenter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.*
import java.io.IOException


class HomeActivity : AppCompatActivity(), ImageInteractionListener {
    lateinit var apiConnection : ApiConnection

    lateinit var userDetailsPresenter : ImagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        apiConnection = ContentLoaderApplication.get(this).getApiConnectionInstance()

        userDetailsPresenter = ImagePresenter(apiConnection,this::onSuccess,this::onError)

        userDetailsPresenter.onCreate()

        setWindowToDrawBehindStatusBar()
    }

    private fun setWindowToDrawBehindStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    private fun onSuccess(list: ArrayList<ImageData>){
        PagerSnapHelper().attachToRecyclerView(contentRecycler)
        contentRecycler.layoutManager = LinearLayoutManager(this)
        val adapter = HomeAdapter(this,ImageViewModelMapper(this,list,this::fetchImage).getMappedObject())
        contentRecycler.adapter = adapter
        adapter.imageInteractionListener = this
    }

    private fun onError(error: AppError){
        Toast.makeText(this,getString(error.displayString),Toast.LENGTH_SHORT).show()
    }

    override fun onSingleTapped(detailsView: DetailsView, model: ImageViewModel) {
        detailsView.rootView.visibility = if(detailsView.rootView.visibility == View.VISIBLE){
            View.GONE
        }else{
            View.VISIBLE
        }
        detailsView.likesText.text = getString(R.string.likes_text, model.width)
        detailsView.resolutionText.text = getString(R.string.resolution_text,model.width,model.height)
    }

    override fun onImageLongPressed(model: ImageViewModel): Boolean {
        model.imageBitmap?.subscribe {
            image ->
            val bitmap = image?.image
            if(bitmap != null){
                setAsWallpaper(bitmap)
            }
        }
        return true
    }


    override fun errorOnLoadingImage(error: AppError) {

    }

    override fun errorOnLoadingUserImage(error: AppError) {

    }


    private fun fetchImage(url: String): Observable<ImageBitmapWithProgress?>? {
        return userDetailsPresenter.loadImage(url)
    }

    //TODO Center crop when setting wallpaper
    private fun setAsWallpaper(wallpaperImage: Bitmap){
        val wallpaperManager = WallpaperManager.getInstance(applicationContext)
        try {
            wallpaperManager.setBitmap(wallpaperImage)
            Toast.makeText(this,getString(R.string.set_wallpaper_success),Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

package com.poovam.contentloader.home.view

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import com.poovam.contentloader.R
import com.poovam.contentloader.common.ContentLoaderApplication
import com.poovam.contentloader.home.domain.ImageData
import com.poovam.contentloader.home.view.adapter.HomeAdapter
import com.poovam.contentloader.home.view.view_model.ImageViewModelMapper
import com.poovam.githubdetails.common.framework.network.ApiConnection
import com.poovam.githubdetails.common.framework.network.ErrorObject
import com.poovam.githubdetails.userdetails.domain.UserDetailsPresenter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    lateinit var apiConnection : ApiConnection

    lateinit var userDetailsPresenter : UserDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        apiConnection = ContentLoaderApplication.get(this).getApiConnectionInstance()

        userDetailsPresenter = UserDetailsPresenter(apiConnection,this::onSuccess,this::onError)

        userDetailsPresenter.onCreate()
    }

    private fun onSuccess(list: ArrayList<ImageData>){

        contentRecycler.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        contentRecycler.adapter = HomeAdapter(this,ImageViewModelMapper(this,list,this::onImageURLChanged).getMappedObject())
    }

    private fun onError(error: ErrorObject){

    }

    companion object {
        var a =0
    }
    private fun onImageURLChanged(url: String): Observable<Bitmap?> {
        Log.d("onImageURLChanged","bit  "+ a++)
        return userDetailsPresenter.loadImage(url)
    }
}

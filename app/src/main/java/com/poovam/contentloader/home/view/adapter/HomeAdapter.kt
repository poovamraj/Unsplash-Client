package com.poovam.contentloader.home.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.poovam.contentloader.R
import com.poovam.contentloader.home.view.ListImageView
import com.poovam.contentloader.home.view.view_model.ImageViewModel

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class HomeAdapter(private val context: Context,private val images: ArrayList<ImageViewModel>) : RecyclerView.Adapter<ListImageView>() {


    override fun onBindViewHolder(holder: ListImageView, position: Int) {
        images[position].imageURL?.subscribe {
           url -> holder.tempText?.text = url
        }

        Log.d("imageContainer is null", (images[position].imageBitmap == null).toString())
        images[position].imageBitmap?.subscribe {
            bitmap -> holder.imageContainer?.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListImageView {
        return ListImageView(LayoutInflater.from(context).inflate(R.layout.image_view, parent,false))
    }

}
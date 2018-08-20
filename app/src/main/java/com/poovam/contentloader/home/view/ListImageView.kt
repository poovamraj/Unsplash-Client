package com.poovam.contentloader.home.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.poovam.contentloader.R

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ListImageView(listImageView: View) : RecyclerView.ViewHolder(listImageView){

    val tempText = listImageView.findViewById<TextView>(R.id.tempText)

    val imageContainer = listImageView.findViewById<ImageView>(R.id.imageContainer)
}
package com.poovam.contentloader.home.view.views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.poovam.contentloader.R

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class ListImageView(listImageView: View) : RecyclerView.ViewHolder(listImageView){

    val userName: TextView = listImageView.findViewById(R.id.userName)

    val imageContainer: ImageView = listImageView.findViewById(R.id.imageContainer)

    val userImageContainer: ImageView = listImageView.findViewById(R.id.userImage)

    val progressBar:ProgressBar = listImageView.findViewById(R.id.progressBar)

    val detailsView: DetailsView = DetailsView(listImageView.findViewById(R.id.detailsView))

}
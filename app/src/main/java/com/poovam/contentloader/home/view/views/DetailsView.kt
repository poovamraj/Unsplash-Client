package com.poovam.contentloader.home.view.views

import android.view.View
import android.widget.TextView
import com.poovam.contentloader.R

/**
 * Created by poovam-5255 on 8/26/2018.
 */

class DetailsView(val rootView: View){
    val likesText: TextView = rootView.findViewById(R.id.likes)

    val resolutionText: TextView = rootView.findViewById(R.id.resolution)
}
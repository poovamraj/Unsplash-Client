package com.poovam.contentloader.home.view.listener

import com.poovam.contentloader.home.view.model.ImageViewModel
import com.poovam.contentloader.home.view.views.DetailsView
import com.poovam.githubdetails.common.framework.network.AppError

/**
 * Created by poovam-5255 on 8/26/2018.
 */

interface ImageInteractionListener {

    fun onSingleTapped(detailsView: DetailsView, model: ImageViewModel)

    fun onImageLongPressed(model: ImageViewModel): Boolean

    fun errorOnLoadingImage(error: AppError)

    fun errorOnLoadingUserImage(error: AppError)
}
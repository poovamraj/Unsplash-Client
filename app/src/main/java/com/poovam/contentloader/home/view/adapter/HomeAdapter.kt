package com.poovam.contentloader.home.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.poovam.contentloader.R
import com.poovam.contentloader.home.view.listener.ImageInteractionListener
import com.poovam.contentloader.home.view.model.ImageViewModel
import com.poovam.contentloader.home.view.model.ImageViewModelHolder
import com.poovam.contentloader.home.view.views.ListImageView
import com.poovam.githubdetails.common.framework.network.AppError

/**
 * Created by poovam-5255 on 8/18/2018.
 */

class HomeAdapter(private val context: Context,
                  private val images: ImageViewModelHolder) : RecyclerView.Adapter<ListImageView>() {



    var imageInteractionListener: ImageInteractionListener? = null

    override fun onBindViewHolder(holder: ListImageView, position: Int) {
        setUserName(holder,images.viewModel[position].userName)

        images.viewModel[position].imageBitmap?.subscribe ({ bitmap ->
            setImage(holder,bitmap?.image,images.viewModel[position])
            setProgress(holder,bitmap?.progress)
        },{ e ->
            setImageNotFound(holder)
            imageInteractionListener?.errorOnLoadingImage(AppError(Exception(AppError.ON_IMG_DOWNLOAD)))
        })

        images.viewModel[position].userImageBitmap?.subscribe ({
            bitmap -> setUserImage(holder,bitmap?.image)
        },{ e ->
            setPlaceHolderUserImage(holder)
            imageInteractionListener?.errorOnLoadingUserImage(AppError(Exception(AppError.ON_USER_IMG_DOWNLOAD)))
        })
    }

    private fun setImage(holder: ListImageView, image: Bitmap?, viewModel: ImageViewModel){
        holder.imageContainer.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.imageContainer.setImageBitmap(image)
        holder.imageContainer.setOnLongClickListener {
            imageInteractionListener?.onImageLongPressed(viewModel) ?: false
        }
        holder.imageContainer.setOnClickListener {
            imageInteractionListener?.onSingleTapped(holder.detailsView,viewModel)
        }
    }

    private fun setImageNotFound(holder: ListImageView){
        holder.imageContainer.scaleType = ImageView.ScaleType.FIT_CENTER
        holder.imageContainer.setImageResource(R.drawable.no_img_found)
    }


    private fun setUserName(holder: ListImageView, userName: String){
        holder.userName.text = userName
    }

    private fun setUserImage(holder: ListImageView, userImage: Bitmap?){
        holder.userImageContainer.setImageBitmap(userImage)
    }

    private fun setPlaceHolderUserImage(holder: ListImageView){
        holder.userImageContainer.setImageResource(R.drawable.user_placeholder)
    }

    private fun setProgress(holder: ListImageView, progress: Int?){
        if(progress!=null && progress != 100){
            holder.progressBar.progress = progress
        }else{
            holder.progressBar.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return images.viewModel.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListImageView {
        return ListImageView(LayoutInflater.from(context).inflate(R.layout.image_view, parent, false))
    }

}
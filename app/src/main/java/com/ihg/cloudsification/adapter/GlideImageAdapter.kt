package com.ihg.cloudsification.adapter


import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ihg.cloudsification.R


import lv.chi.photopicker.loader.ImageLoader

class GlideImageLoader: ImageLoader {

    override fun loadImage(context: Context, view: ImageView, uri: Uri) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .into(view)
    }
}
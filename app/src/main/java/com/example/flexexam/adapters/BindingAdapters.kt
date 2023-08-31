package com.example.flexexam.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.flexexam.R
import com.example.flexexam.util.Constants.POSTER_IMG_URL

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("posterImageUrl")
    fun loadPosterImage(view: ImageView, url: String) {
        Glide
            .with(view.context)
            .load("$POSTER_IMG_URL$url")
            .centerCrop()
            .placeholder(R.drawable.ic_android)
            .into(view)
    }
}
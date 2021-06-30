package com.arvind.newsapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import javax.inject.Inject

class BindingAdaptres @Inject constructor() {

    companion object {
        @BindingAdapter("urlToImage")
        @JvmStatic
        fun loadImage(view: ImageView, ServiceIconURL: String) {
            Glide.with(view.context)
                .load(ServiceIconURL)
                .into(view)
        }
    }
}
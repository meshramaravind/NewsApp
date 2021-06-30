package com.arvind.newsapp.binding


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arvind.newsapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject


class BindingAdaptres @Inject constructor() {
    companion object {
        @BindingAdapter("urlToImage")
        @JvmStatic
        fun loadImage(view: ImageView, urlToImage: String?) {
            try {
                Glide.with(view.context).setDefaultRequestOptions(RequestOptions())
                    .load(urlToImage).placeholder(R.drawable.bg_image_loader_)
                    .error(R.drawable.bg_image_loader_)
                    .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
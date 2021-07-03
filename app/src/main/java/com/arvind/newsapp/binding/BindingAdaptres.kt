package com.arvind.newsapp.binding


import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.arvind.newsapp.R
import com.arvind.newsapp.utils.formatTimeAgo
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
                    .load(urlToImage).placeholder(R.drawable.bg_image_loader)
                    .error(R.drawable.bg_image_loader)
                    .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @BindingAdapter("url")
        @JvmStatic
        fun loadsourceImage(view: ImageView, url: String?) {
            try {
                val iconUrl = "https://besticon-demo.herokuapp.com/icon?url=%s&size=80..120..200"
                val url = java.lang.String.format(iconUrl, Uri.parse(url).authority)
                Glide.with(view.context).setDefaultRequestOptions(RequestOptions().circleCrop())
                    .load(url).placeholder(R.drawable.bg_image_loader_source)
                    .error(R.drawable.bg_image_loader_source)
                    .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        @BindingAdapter("urltonewssource")
        @JvmStatic
        fun loadnewssourcetoImage(view: ImageView, urltonewssource: String?) {
            try {
                val iconUrl = "https://besticon-demo.herokuapp.com/icon?url=%s&size=80..120..200"
                val url = java.lang.String.format(iconUrl, Uri.parse(urltonewssource).authority)
                Glide.with(view.context).setDefaultRequestOptions(RequestOptions().circleCrop())
                    .load(url).placeholder(R.drawable.bg_image_loader_source)
                    .error(R.drawable.bg_image_loader_source)
                    .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @SuppressLint("SimpleDateFormat")
        @BindingAdapter("timeAgoFormat")
        @JvmStatic
        fun convertToTimeAgoFormat(textView: TextView, time: String) {
            try {
                val timeAgo = formatTimeAgo(time)
                textView.text = timeAgo
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
}
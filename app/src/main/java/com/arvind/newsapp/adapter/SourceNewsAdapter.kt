package com.arvind.newsapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arvind.newsapp.databinding.ItemsSourceNewsBinding
import com.arvind.newsapp.response.SourcesNews


class SourceNewsAdapter(private val context: Context) :
    RecyclerView.Adapter<SourceNewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SourceNewsAdapter.NewsViewHolder {
        return NewsViewHolder(
            ItemsSourceNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SourceNewsAdapter.NewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.bind(news)

    }

    private val differCallback =
        object : DiffUtil.ItemCallback<SourcesNews>() {
            override fun areItemsTheSame(
                oldItem: SourcesNews,
                newItem: SourcesNews
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: SourcesNews,
                newItem: SourcesNews
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(private val itemsSourceNewsBinding: ItemsSourceNewsBinding) :
        RecyclerView.ViewHolder(itemsSourceNewsBinding.root) {
        fun bind(sourceResponse: SourcesNews) {
            itemsSourceNewsBinding.apply {
                itemsSourceNewsBinding.source = sourceResponse
                itemsSourceNewsBinding.executePendingBindings()

                itemsSourceNewsBinding.root.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(sourceResponse.url))
                }

            }

        }
    }
}
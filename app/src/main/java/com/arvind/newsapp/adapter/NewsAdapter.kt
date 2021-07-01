package com.arvind.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arvind.newsapp.R
import com.arvind.newsapp.databinding.ItemsNewsBinding
import com.arvind.newsapp.databinding.LayoutBottomSaveditemsBinding
import com.arvind.newsapp.response.Article
import com.arvind.newsapp.view.news.NewsFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var bottomSheetDialog: BottomSheetDialog? = null
    private lateinit var layoutBottomSaveditemsBinding: LayoutBottomSaveditemsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        return NewsViewHolder(
            ItemsNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.bind(news)

    }

    private val differCallback =
        object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(private val itemsNewsBinding: ItemsNewsBinding) :
        RecyclerView.ViewHolder(itemsNewsBinding.root) {
        fun bind(articleResponse: Article) {
            itemsNewsBinding.apply {
                itemsNewsBinding.news = articleResponse
                itemsNewsBinding.executePendingBindings()

                ivMoreoptions.setOnClickListener {
                    if (bottomSheetDialog == null) {
                        bottomSheetDialog =
                            BottomSheetDialog(context)
                        layoutBottomSaveditemsBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(context),
                            R.layout.layout_bottom_saveditems,
                            null,
                            false
                        )
                        bottomSheetDialog!!.setContentView(
                            layoutBottomSaveditemsBinding.root
                        )

                    }
                    bottomSheetDialog!!.show()
                }

            }

        }
    }
}
package com.arvind.newsapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arvind.newsapp.R
import kotlinx.android.synthetic.main.items_category_card.view.*

class CategoriesAdapter(private val categories: List<String>) :
    RecyclerView.Adapter<CategoryViewHolder>() {

    private var selectedPosition: Int = 0

    private var onItemClickListener: ((String) -> Unit)? = null

    fun onItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.items_category_card, parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categories[position]

        holder.itemView.apply {
            tv_categoryName.text = "#$currentCategory"

            if (selectedPosition == position) {
                categoryLayout.setBackgroundResource(R.drawable.bg_category)
                tv_categoryName.setTextColor(Color.parseColor("#ffffff"))
            } else {
                categoryLayout.setBackgroundResource(0)
                tv_categoryName.setTextColor(Color.parseColor("#263238"))
            }

            setOnClickListener {
                onItemClickListener?.let { it(currentCategory) }

                if (selectedPosition >= 0) {
                    notifyItemChanged(selectedPosition)
                }
                selectedPosition = holder.adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int = categories.size
}

class CategoryViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem)
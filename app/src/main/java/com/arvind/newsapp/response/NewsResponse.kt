package com.arvind.newsapp.response

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsResponse(
    val status: String,
    val totalResult: Int,
    val articles: List<Article>,
)

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)

data class Source(
    val id: String,
    val name: String,
)

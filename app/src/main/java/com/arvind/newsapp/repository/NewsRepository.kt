package com.arvind.newsapp.repository

import com.arvind.newsapp.webapi.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getNews() = apiService.getNews()
}
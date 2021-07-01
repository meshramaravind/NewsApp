package com.arvind.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arvind.newsapp.app.NewsApp
import com.arvind.newsapp.repository.NewsRepository
import com.arvind.newsapp.response.NewsResponse
import com.arvind.newsapp.response.SourceResponse
import com.arvind.newsapp.storage.UIModeDataStore
import com.arvind.newsapp.utils.Resource
import com.arvind.newsapp.utils.categories
import com.arvind.newsapp.utils.hasInternetConnection
import com.arvind.newsapp.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application) {

    private val uiModeDataStore = UIModeDataStore(application)

    // get ui mode
    val getUIMode = uiModeDataStore.uiMode

    // save ui mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiModeDataStore.saveToDataStore(isNightMode)
        }
    }

    val newsData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    val newsSourcesData: MutableLiveData<Resource<SourceResponse>> = MutableLiveData()

    private val newsDataTemp = MutableLiveData<Resource<NewsResponse>>()

    private var news = 1
    private var headlinenews = 1
    private var searchNewsPage = 1

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        fetchNews()
    }

    fun getHeadlinesNews(category: String = categories.first()) = viewModelScope.launch {
        fetchheadlinews(category)
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        fetchsearchnews(searchQuery)
    }

    fun getSourcesNews() = viewModelScope.launch {
        fetchSourcesNews()
    }

    private suspend fun fetchNews() {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApp>()) {
                val response = repository.getNews()
                newsData.postValue(handleNewsResponse(response))
            } else {
                newsData.postValue(Resource.Error("No Internet Connection"))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
                else -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
            }
        }
    }

    private suspend fun fetchheadlinews(category: String = categories.first()) {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApp>()) {
                val response = repository.getTopHeadlines(category, headlinenews)
                newsData.postValue(handleNewsResponse(response))
            } else {
                newsData.postValue(Resource.Error("No Internet Connection"))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
                else -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
            }
        }
    }

    private suspend fun fetchsearchnews(searchQuery: String) {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApp>()) {
                val response = repository.getSearchNews(searchQuery, searchNewsPage)
                newsData.postValue(handleNewsResponse(response))
            } else {
                newsData.postValue(Resource.Error("No Internet Connection"))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
                else -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
            }
        }
    }

    private suspend fun fetchSourcesNews() {
        newsSourcesData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApp>()) {
                val response = repository.getSourceNews()
                newsSourcesData.postValue(handleSourceNewsResponse(response))
            } else {
                newsSourcesData.postValue(Resource.Error("No Internet Connection"))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsSourcesData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
                else -> newsSourcesData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
            }
        }
    }

    private fun handleSourceNewsResponse(response: Response<SourceResponse>): Resource<SourceResponse>? {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }
}
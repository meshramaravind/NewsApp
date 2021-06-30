package com.arvind.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arvind.newsapp.app.NewsApp
import com.arvind.newsapp.repository.NewsRepository
import com.arvind.newsapp.response.NewsResponse
import com.arvind.newsapp.storage.UIModeDataStore
import com.arvind.newsapp.utils.Resource
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

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        fetchNews()
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

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }
}
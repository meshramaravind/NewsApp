package com.arvind.newsapp.response

import com.google.gson.annotations.SerializedName

data class SourceResponse(@SerializedName("status") val status : String,
                          @SerializedName("sources") val sources : List<SourcesNews>)

data class SourcesNews (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("url") val url : String,
    @SerializedName("category") val category : String,
    @SerializedName("language") val language : String,
    @SerializedName("country") val country : String
)

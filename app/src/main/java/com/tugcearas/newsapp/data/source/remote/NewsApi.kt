package com.tugcearas.newsapp.data.source.remote

import com.tugcearas.newsapp.data.model.NewsResponseModel
import com.tugcearas.newsapp.util.constants.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(Constants.ENDPOINT_HEADLINES)
    suspend fun getBreakingNews(
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String = Constants.API_KEY,
        @Query("country")
        country: String = "us"
    ):Response<NewsResponseModel>

    @GET(Constants.ENDPOINT_EVERYTHING)
    suspend fun getEverythingNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponseModel>
}
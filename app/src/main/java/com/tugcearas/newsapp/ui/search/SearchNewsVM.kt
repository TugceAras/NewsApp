package com.tugcearas.newsapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.newsapp.data.model.NewsResponseModel
import com.tugcearas.newsapp.data.repository.NewsRepository
import com.tugcearas.newsapp.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchNewsVM @Inject constructor(private val newsRepo:NewsRepository): ViewModel() {

    val searchNews: MutableLiveData<Resource<NewsResponseModel>> = MutableLiveData()
    private val pageNumber = 1

    fun getNewsEverything(searchQuery:String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val getEverythingResponse = newsRepo.getEverythingFromApi(searchQuery,pageNumber)
        searchNews.postValue(handleSearchNews(getEverythingResponse))
    }

    private fun handleSearchNews(searchResponse: Response<NewsResponseModel>):Resource<NewsResponseModel>{
        if (searchResponse.isSuccessful){
            searchResponse.body()?.let {response->
                return response.let {
                    Resource.Success(it)
                }
            }
        }
        return Resource.Error(searchResponse.message())
    }
}
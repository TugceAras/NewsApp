package com.tugcearas.newsapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.newsapp.data.model.Article
import com.tugcearas.newsapp.data.repository.NewsRepository
import com.tugcearas.newsapp.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val newsRepo:NewsRepository): ViewModel() {

    val getNews: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    private val pageNumber=1

    fun getBreakingNews() = viewModelScope.launch {
        getNews.postValue(Resource.Loading())
        val getNewsResponse = newsRepo.getBreakingNewsFromApi(pageNumber)
        getNews.postValue(handleNewsResponse(getNewsResponse))
    }

    private fun handleNewsResponse(response: Resource<List<Article>>) = when(response){
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(response.data.orEmpty())
            is Resource.Error -> Resource.Error(response.message.orEmpty())
    }
}

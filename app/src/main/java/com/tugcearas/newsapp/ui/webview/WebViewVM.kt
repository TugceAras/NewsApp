package com.tugcearas.newsapp.ui.webview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.newsapp.data.model.Article
import com.tugcearas.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewVM @Inject constructor(private val newsRepo:NewsRepository): ViewModel() {

    val isAddedFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun addFavoriteArticle(article: Article) = viewModelScope.launch {
        isAddedFavorite.value =  newsRepo.addArticle(article)
    }
}


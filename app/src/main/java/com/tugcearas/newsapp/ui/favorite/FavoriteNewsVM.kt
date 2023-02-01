package com.tugcearas.newsapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.newsapp.data.model.Article
import com.tugcearas.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(private val newsRepo:NewsRepository): ViewModel() {

    fun addArticle(article: Article) = viewModelScope.launch {
        newsRepo.addArticle(article)
    }

    fun getFavArticles() = newsRepo.getFavArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepo.deleteArticle(article)
    }
}
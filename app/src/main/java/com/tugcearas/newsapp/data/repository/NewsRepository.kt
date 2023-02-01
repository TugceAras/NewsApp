package com.tugcearas.newsapp.data.repository

import com.tugcearas.newsapp.data.source.local.ArticleDao
import com.tugcearas.newsapp.data.model.Article
import com.tugcearas.newsapp.data.source.remote.NewsApi
import com.tugcearas.newsapp.util.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Benimki

class NewsRepository @Inject constructor(private val database: ArticleDao, private val newsApi: NewsApi) {

    suspend fun getBreakingNewsFromApi(pageNumber:Int):Resource<List<Article>>{

       try {
           val articleTitles:List<String>
           withContext(Dispatchers.IO){
               articleTitles = database.getArticleTitles()
           }
           val breakingNewList = mutableListOf<Article>()

           newsApi.getBreakingNews(pageNumber).body()?.articles?.map {
               breakingNewList.add(
                   Article(
                       author = it.author,
                       content = it.content,
                       description = it.description,
                       publishedAt = it.publishedAt,
                       source = it.source,
                       title = it.title,
                       url = it.url,
                       urlToImage = it.urlToImage,
                       isFav = articleTitles.contains(it.title)
                   )
               )
           }

           return Resource.Success(breakingNewList)
       }
       catch (e:Exception){
           return Resource.Error(e.message.orEmpty())
       }
    }
    suspend fun getEverythingFromApi(searchQuery:String,pageNumber:Int) =
        newsApi.getEverythingNews(searchQuery,pageNumber)

    suspend fun addArticle(article: Article):Boolean {
        val articleTitles: List<String>
        withContext(Dispatchers.IO){
            articleTitles = database.getArticleTitles()
        }
        return if(!articleTitles.contains(article.title)){
            database.addArticle(article)
            true
        }
        else{
            false
        }
    }

    fun getFavArticles() = database.getFavArticles()

    suspend fun deleteArticle(article: Article) =  database.deleteArticle(article)
}
package com.tugcearas.newsapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tugcearas.newsapp.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: Article) : Long

    @Query("SELECT * FROM articleTable")
    fun getFavArticles() : LiveData<List<Article>>

    @Query("SELECT title FROM articleTable")
    fun getArticleTitles():List<String>

    @Delete
    suspend fun deleteArticle(article: Article)
}
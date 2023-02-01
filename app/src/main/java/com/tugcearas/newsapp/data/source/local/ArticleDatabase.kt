package com.tugcearas.newsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tugcearas.newsapp.data.model.Article

@Database(entities = [Article::class], version = 4, exportSchema = false)
@TypeConverters(TypeConvertor::class)
abstract class ArticleDatabase:RoomDatabase() {

    abstract fun getArticleFromDao() : ArticleDao
}
package com.tugcearas.newsapp.di

import android.content.Context
import androidx.room.Room
import com.tugcearas.newsapp.data.source.local.ArticleDao
import com.tugcearas.newsapp.data.source.local.ArticleDatabase
import com.tugcearas.newsapp.data.source.remote.NewsApi
import com.tugcearas.newsapp.util.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance():Retrofit {
       return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit:Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase =
        Room.databaseBuilder(context, ArticleDatabase::class.java,"articleDatabase")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideArticleDao(articleDb: ArticleDatabase): ArticleDao {
        return articleDb.getArticleFromDao()
    }
}
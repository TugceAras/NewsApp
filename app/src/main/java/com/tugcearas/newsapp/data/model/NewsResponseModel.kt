package com.tugcearas.newsapp.data.model

import androidx.room.ColumnInfo

data class NewsResponseModel(
    @ColumnInfo(name ="articles" )
    val articles: List<Article>,
    @ColumnInfo(name ="status" )
    val status: String,
    @ColumnInfo(name ="totalResults" )
    val totalResults: Int
)
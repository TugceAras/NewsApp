package com.tugcearas.newsapp.data.source.local

import androidx.room.TypeConverter
import com.tugcearas.newsapp.data.model.Source

class TypeConvertor {

    @TypeConverter
    fun convertSourceType(source: Source):String?{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name)
    }
}
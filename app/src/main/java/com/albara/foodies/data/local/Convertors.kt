package com.albara.foodies.data.local

import androidx.room.TypeConverter

class Convertors {
    @TypeConverter
    fun fromList(list: List<Int>): String {
        return if (list.isEmpty()) ""
        else list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<Int> {
        return if (data.isEmpty()) return emptyList()
        else data.split(",").map { it.toInt() }
    }
}
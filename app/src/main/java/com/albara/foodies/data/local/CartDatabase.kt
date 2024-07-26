package com.albara.foodies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.albara.foodies.data.local.entity.CartEntity


@Database(
    entities = [CartEntity::class],
    version = 1
)
@TypeConverters(Convertors::class)
abstract class CartDatabase : RoomDatabase() {
    abstract val dao : CartDao
}
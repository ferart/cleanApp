package com.example.data.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.data.models.BusinessEntity

@Database(entities = arrayOf(BusinessEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessDAO(): BusinessDAO

}
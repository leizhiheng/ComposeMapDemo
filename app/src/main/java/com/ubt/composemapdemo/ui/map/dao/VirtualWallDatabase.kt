package com.ubt.composemapdemo.ui.map.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubtrobot.mapview.VirtualWall

@Database(entities = [VirtualWall::class], version = 1, exportSchema = false)
abstract class VirtualWallDatabase : RoomDatabase() {
    abstract fun virtualWallDao(): VirtualWallDao

    companion object {
        @Volatile
        private var instance: VirtualWallDatabase? = null

        private const val DATABASE_NAME = "virtual_wall_db"

        fun getInstance(context: Context): VirtualWallDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, VirtualWallDatabase::class.java, DATABASE_NAME).build().also { instance = it }
            }
        }
    }
}
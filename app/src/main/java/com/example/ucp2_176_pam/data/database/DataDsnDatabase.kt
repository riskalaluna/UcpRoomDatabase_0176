package com.example.ucp2_176_pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2_176_pam.data.dao.DosenDao
import com.example.ucp2_176_pam.data.entity.Dosen
import kotlin.concurrent.Volatile

@Database(entities = [Dosen::class], version = 1, exportSchema = false)
abstract class DataDsnDatabase : RoomDatabase() {
    abstract fun dosenDao(): DosenDao

    companion object{
        @Volatile
        private var Instance: DataDsnDatabase? = null

        fun getDatabase(context: Context): DataDsnDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DataDsnDatabase::class.java,
                    "DataDsnDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
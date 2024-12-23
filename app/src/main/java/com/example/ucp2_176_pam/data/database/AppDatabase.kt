package com.example.ucp2_176_pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2_176_pam.data.dao.DosenDao
import com.example.ucp2_176_pam.data.dao.MataKuliahDao
import com.example.ucp2_176_pam.data.entity.Dosen
import com.example.ucp2_176_pam.data.entity.MataKuliah

//database utama yang bertanggung jawab menyediakan akses ke DAO dan menyimpan data
@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dosenDao(): DosenDao
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
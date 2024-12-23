package com.example.ucp2_176_pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2_176_pam.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

//DAO menyediakan metode utama untuk mengelola data di tabel dosen
//yang bisa dilakukan menambah, mengambil semua data dan mengambil data berdasarkan NIDN
@Dao
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    //getAllDosen
    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>

    //getDosen
    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>
}
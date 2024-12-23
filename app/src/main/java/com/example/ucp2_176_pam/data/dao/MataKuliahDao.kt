package com.example.ucp2_176_pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2_176_pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

////DAO menyediakan metode utama untuk mengelola data di tabel matakuliah
//menambah, mengambil semua data, mengambil data berdasarkan kode, mengedit data dan menghapus data
@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMataKuliah(kode: String): Flow<MataKuliah>

    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}
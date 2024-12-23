package com.example.ucp2_176_pam.repository

import com.example.ucp2_176_pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

//RepositoryDsn bertindak sebagai kontrak untuk pengelolaan data dosen,
interface RepositoryMK {
    suspend fun insertMK(mataKuliah: MataKuliah)

    fun getAllMK(): Flow<List<MataKuliah>>

    fun getMK(kode: String): Flow<MataKuliah>

    suspend fun deleteMK(mataKuliah: MataKuliah)

    suspend fun updateMK(mataKuliah: MataKuliah)
}
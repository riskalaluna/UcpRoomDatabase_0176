package com.example.ucp2_176_pam.repository

import com.example.ucp2_176_pam.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDsn {
    suspend fun insertDsn(dosen: Dosen)

    //getAllDsn
    fun getAllDsn(): Flow<List<Dosen>>

    //getDsn
    fun getDsn(nim: String): Flow<Dosen>
}
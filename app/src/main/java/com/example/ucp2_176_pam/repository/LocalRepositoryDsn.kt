package com.example.ucp2_176_pam.repository

import com.example.ucp2_176_pam.data.dao.DosenDao
import com.example.ucp2_176_pam.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

//merupakan implementasi dari RepositoryDsn yang mengelola data dosen
//Repository ini menggunakan DosenDao untuk berinteraksi dengan database dan menyediakan data
//melakukan operasi CRUD (dibagian dosen hanya menambah dan mengambil data)
class LocalRepositoryDsn (
    private val dosenDao: DosenDao
) : RepositoryDsn {

    override suspend fun insertDsn(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    //getAllDsn
    override fun getAllDsn(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override fun getDsn(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }
}
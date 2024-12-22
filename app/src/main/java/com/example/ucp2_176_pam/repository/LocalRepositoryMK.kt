package com.example.ucp2_176_pam.repository

import com.example.ucp2_176_pam.data.dao.MataKuliahDao
import com.example.ucp2_176_pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMK (
    private val mataKuliahDao: MataKuliahDao
) : RepositoryMK {
    override suspend fun insertMK(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }

    override fun getAllMK(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMataKuliah()
    }

    override fun getMK(kode: String): Flow<MataKuliah> {
        return mataKuliahDao.getMataKuliah(kode)
    }

    override suspend fun deleteMK(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMataKuliah(mataKuliah)
    }

    override suspend fun updateMK(mataKuliah: MataKuliah) {
        mataKuliahDao.updateMataKuliah(mataKuliah)
    }
}
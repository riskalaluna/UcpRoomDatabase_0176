package com.example.ucp2_176_pam.dependenciesinjection

import android.content.Context
import com.example.ucp2_176_pam.data.database.AppDatabase
import com.example.ucp2_176_pam.repository.LocalRepositoryDsn
import com.example.ucp2_176_pam.repository.LocalRepositoryMK
import com.example.ucp2_176_pam.repository.RepositoryDsn
import com.example.ucp2_176_pam.repository.RepositoryMK

interface InterfaceContainerApp {
    val repositoryDsn: RepositoryDsn
    val repositoryMK: RepositoryMK
}
class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(AppDatabase.getDatabase(context).dosenDao())
    }

    override val repositoryMK: RepositoryMK by lazy {
        LocalRepositoryMK(AppDatabase.getDatabase(context).mataKuliahDao())
    }
}



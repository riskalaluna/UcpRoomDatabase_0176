package com.example.ucp2_176_pam.dependenciesinjection

import android.content.Context
import com.example.ucp2_176_pam.data.database.DataDsnDatabase
import com.example.ucp2_176_pam.repository.LocalRepositoryDsn
import com.example.ucp2_176_pam.repository.RepositoryDsn

interface InterfaceContainerApp {
    val repositoryDsn: RepositoryDsn
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(DataDsnDatabase.getDatabase(context).dosenDao())
    }
}
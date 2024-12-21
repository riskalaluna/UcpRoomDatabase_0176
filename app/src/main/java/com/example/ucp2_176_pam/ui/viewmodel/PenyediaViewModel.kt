package com.example.ucp2_176_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2_176_pam.DataDsnApp
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DosenViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                DataDsnApp().containerApp.repositoryDsn
            )
        }
    }
}

fun CreationExtras.DataDsnApp(): DataDsnApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DataDsnApp)
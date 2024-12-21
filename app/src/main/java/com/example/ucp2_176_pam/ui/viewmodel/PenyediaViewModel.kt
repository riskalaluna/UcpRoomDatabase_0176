package com.example.ucp2_176_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2_176_pam.DataDsnApp
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DetailDsnViewModel
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2_176_pam.ui.viewmodel.dosen.HomeDsnViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                DataDsnApp().containerApp.repositoryDsn
            )
        }

        initializer {
            HomeDsnViewModel(
                DataDsnApp().containerApp.repositoryDsn
            )
        }

        initializer {
            DetailDsnViewModel(
                createSavedStateHandle(),
                DataDsnApp().containerApp.repositoryDsn
            )
        }
    }
}

fun CreationExtras.DataDsnApp(): DataDsnApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DataDsnApp)
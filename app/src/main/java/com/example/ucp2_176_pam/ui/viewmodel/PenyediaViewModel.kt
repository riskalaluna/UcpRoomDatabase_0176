package com.example.ucp2_176_pam.ui.viewmodel

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2_176_pam.DataDsnApp
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2_176_pam.ui.viewmodel.dosen.HomeDsnViewModel
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.DetailMataKuliahViewModel
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.HomeMataKuliahViewModel
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MataKuliahViewModel
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.UpdateMataKuliahViewModel

//membuat dan menyimpan seluruh viewmodel
object PenyediaViewModel{

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
            MataKuliahViewModel(
                DataDsnApp().containerApp.repositoryMK,
                DataDsnApp().containerApp.repositoryDsn
            )
        }

        initializer {
            HomeMataKuliahViewModel(
                DataDsnApp().containerApp.repositoryMK
            )
        }

        initializer {
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                DataDsnApp().containerApp.repositoryMK
            )
        }

        initializer {
            UpdateMataKuliahViewModel(
                createSavedStateHandle(),
                DataDsnApp().containerApp.repositoryMK,
                DataDsnApp().containerApp.repositoryDsn
            )
        }
    }
}

fun CreationExtras.DataDsnApp(): DataDsnApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DataDsnApp)

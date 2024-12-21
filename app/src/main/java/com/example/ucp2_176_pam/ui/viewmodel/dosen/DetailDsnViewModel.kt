package com.example.ucp2_176_pam.ui.viewmodel.dosen

import com.example.ucp2_176_pam.data.entity.Dosen

fun Dosen.toDetailUiEvent(): DosenEvent {
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin
    )
}
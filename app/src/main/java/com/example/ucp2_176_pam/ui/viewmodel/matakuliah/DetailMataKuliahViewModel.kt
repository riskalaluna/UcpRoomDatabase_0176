package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import com.example.ucp2_176_pam.data.entity.MataKuliah


fun MataKuliah.toDetailUiEvent(): MataKuliahEvent {
    return MataKuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}
package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import com.example.ucp2_176_pam.data.entity.MataKuliah

data class MataKuliahEvent (
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah (
    kode = kode,
    nama = nama,
    sks = sks,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)